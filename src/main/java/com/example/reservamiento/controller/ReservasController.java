package com.example.reservamiento.controller;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Reserva;
import com.example.reservamiento.repository.AlojamientosRepository;
import com.example.reservamiento.repository.UsuariosRepository;
import com.example.reservamiento.utilidades.UtilidadesSesion;
import com.example.reservamiento.service.ReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.List;

@Controller
public class ReservasController {

    @Autowired
    private ReservasService reservasService;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private AlojamientosRepository alojamientosRepository;

    @RequestMapping("/reservar")
    public ModelAndView nuevaReserva(@RequestParam String id_alojamiento, HttpSession session) {
        Alojamiento alojamiento = alojamientosRepository.findTopByIdAlojamiento(id_alojamiento);
        Reserva reserva = (Reserva)session.getAttribute("reserva");
        if (reserva==null) return new ModelAndView("redirect:/");
        reserva.setUsuario(usuariosRepository.findTopByUsername(UtilidadesSesion.getUserName(session)));
        reserva.setAlojamiento(alojamiento);
        reserva.setHabitaciones((int)Math.ceil((double)reserva.getPersonas()/alojamiento.getHuespedes()));
        reserva.setImporte(alojamiento.getPrecio()*reserva.getHabitaciones()*(int)(Duration.between(reserva.getFecha_inicio().atStartOfDay(), reserva.getFecha_fin().atStartOfDay()).toDays()));
        ModelAndView modelAndView = new ModelAndView("reservar");
        modelAndView.addObject("alojamiento", alojamiento);
        modelAndView.addObject("reserva", reserva);
        return modelAndView;
    }

    @PostMapping("/guardar-reserva")
    public String guardarReserva(@RequestParam String id_reserva, @RequestParam String observaciones, @RequestParam(required = false) String pagado, HttpSession session) {
        Reserva reserva = (id_reserva.equals("0")) ? (Reserva)session.getAttribute("reserva") : reservasService.obtenerPorId(id_reserva);
        if (reserva==null) return "redirect:/";
        reserva.setObservaciones(observaciones.length()==0 ? null : observaciones);
        reserva.setPagado(pagado!=null && pagado.equals("on"));
        reservasService.guardarReserva(reserva);
        session.removeAttribute("reserva");
        session.removeAttribute("destino");
        return "redirect:/reservas";
    }

    @RequestMapping( "/reservas")
    public ModelAndView obtenerReservas(@RequestParam(required = false) String respuesta, HttpSession session) {
        int idusuario = usuariosRepository.findTopByUsername(UtilidadesSesion.getUserName(session)).getId_usuario();
        List<Reserva> listReservasActivas = reservasService.obtenerReservasActivasUsuario(idusuario);
        List<Reserva> listReservasPasadas = reservasService.obtenerReservasPasadasUsuario(idusuario);
        ModelAndView modelAndView = new ModelAndView("reservas");
        modelAndView.addObject("reservasactivas", listReservasActivas);
        modelAndView.addObject("reservaspasadas", listReservasPasadas);
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        return modelAndView;
    }

    @PostMapping("/gestionar")
    public ModelAndView gestionarReservas(@RequestParam String id_reserva) {
        Reserva reserva = reservasService.obtenerPorId(id_reserva);
        ModelAndView modelAndView = new ModelAndView("reservar");
        modelAndView.addObject("reserva", reserva);
        modelAndView.addObject("alojamiento", reserva.getAlojamiento());
        return modelAndView;
    }
    @PostMapping("/cancelar")
    public ModelAndView cancelarReserva(@RequestParam String id_reserva, @RequestParam(required = false) String origen) {
        reservasService.cancelarPorId(id_reserva);
        return origen.equals("admin") ? new ModelAndView("redirect:/admin/reservas?respuesta=cancelada") : new ModelAndView("redirect:/reservas?respuesta=cancelada");
    }

    @GetMapping("/admin/reservas")
    public ModelAndView administrarReservas(@RequestParam(required = false) String respuesta, @RequestParam(required = false) String q) {
        List<Reserva> listaReservas = (q==null) ? reservasService.obtenerReservas() : reservasService.buscarReservas(q);
        ModelAndView modelAndView = new ModelAndView("admin/reservas");
        modelAndView.addObject("reservas", listaReservas);
        modelAndView.addObject("reservaedit", new Reserva());
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        modelAndView.addObject("query", (q==null) ? "" : q);
        return modelAndView;
    }
    @PostMapping("/admin/reservas")
    public ModelAndView editarReserva(@RequestParam(required = false) String respuesta, @RequestParam String id_reserva) {
        List<Reserva> listaReservas = reservasService.obtenerReservas();
        ModelAndView modelAndView = new ModelAndView("admin/reservas");
        modelAndView.addObject("reservas", listaReservas);
        modelAndView.addObject("reservaedit", reservasService.obtenerPorId(id_reserva));
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        return modelAndView;
    }

    @PostMapping("/admin/agregarreserva")
    public String agregarReserva(@ModelAttribute Reserva nuevareserva, @RequestParam String username, @RequestParam String id_alojamiento, @RequestParam String fechainicio, @RequestParam String fechafin){
        if (!usuariosRepository.existsByUsername(username)) return "redirect:/admin/reservas?respuesta=errorusuario";
        Alojamiento alojamiento = alojamientosRepository.findTopByIdAlojamiento(id_alojamiento);
        if (alojamiento == null) return "redirect:/admin/reservas?respuesta=erroralojamiento";
        boolean esNuevo = nuevareserva.getId_reserva()==0;
        Reserva oldreserva = (esNuevo) ? nuevareserva : reservasService.obtenerPorId(Integer.toString(nuevareserva.getId_reserva()));
        oldreserva.setUsuario(usuariosRepository.findTopByUsername(username));
        oldreserva.setAlojamiento(alojamiento);
        oldreserva.setFecha_inicio(fechainicio);
        oldreserva.setFecha_fin(fechafin);
        oldreserva.setPersonas(nuevareserva.getPersonas());
        oldreserva.setObservaciones("Modificado por un administrador");
        if (esNuevo) oldreserva.setFecha_reserva();
        oldreserva.setHabitaciones((int)Math.ceil((double)nuevareserva.getPersonas()/alojamiento.getHuespedes()));
        oldreserva.setImporte(alojamiento.getPrecio()*oldreserva.getHabitaciones()*(int)(Duration.between(oldreserva.getFecha_inicio().atStartOfDay(), oldreserva.getFecha_fin().atStartOfDay()).toDays()));
        oldreserva.setPagado(false);
        oldreserva.setActivo(true);
        return (reservasService.guardarReserva(oldreserva)!=null) ? ((esNuevo) ? "redirect:/admin/reservas?respuesta=agregada" : "redirect:/admin/reservas?respuesta=editada") : "redirect:/admin/reservas?respuesta=error";
    }
}
