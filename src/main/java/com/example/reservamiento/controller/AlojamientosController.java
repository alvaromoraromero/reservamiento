package com.example.reservamiento.controller;


import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Reserva;
import com.example.reservamiento.model.Usuario;
import com.example.reservamiento.repository.UsuariosRepository;
import com.example.reservamiento.service.AlojamientosService;
import com.example.reservamiento.service.ComentariosService;
import com.example.reservamiento.service.ReservasService;
import com.example.reservamiento.utilidades.UtilidadesSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AlojamientosController {

    @Autowired
    private ReservasService reservasService;
    @Autowired
    private AlojamientosService alojamientosService;
    @Autowired
    private ComentariosService comentariosService;
    @Autowired
    private UsuariosRepository usuariosRepository;

    @RequestMapping("/")
    public ModelAndView pantallaInicio(HttpSession session) {
        Reserva reserva = (Reserva)session.getAttribute("reserva");
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("ubicaciones", alojamientosService.desplegableUbicaciones());
        modelAndView.addObject("reserva", reserva==null?new Reserva():reserva);
        modelAndView.addObject("destino", session.getAttribute("destino"));
        modelAndView.addObject("reservastop", reservasService.obtenerReservasTop());
        return modelAndView;
    }

    @RequestMapping("/alojamientos")
    public ModelAndView obtenerAlojamientos(@RequestParam(required = false) String idalojamiento, @RequestParam String destino, @RequestParam String fechainicio, @RequestParam String fechafin, @RequestParam int personas, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("alojamientos");
        List<Alojamiento> listAlojamiento = alojamientosService.buscarAlojamientos(destino, fechainicio, fechafin, personas);
        if (idalojamiento!=null) {
            int index = listAlojamiento.indexOf(alojamientosService.buscarPorId(idalojamiento));
            if (index!=-1) {
                Alojamiento a = listAlojamiento.get(index);
                listAlojamiento.clear();
                listAlojamiento.add(a);
                modelAndView.addObject("idalojamiento", true);
            }
        }
        Reserva reserva = new Reserva(fechainicio, fechafin, personas);
        session.setAttribute("reserva", reserva);
        session.setAttribute("destino", destino);
        modelAndView.addObject("alojamientos", listAlojamiento);
        modelAndView.addObject("ubicaciones", alojamientosService.desplegableUbicaciones());
        modelAndView.addObject("reserva", reserva);
        modelAndView.addObject("destino", destino);
        return modelAndView;
    }

    @GetMapping("/alojamiento")
    public ModelAndView pantallaAlojamiento(@RequestParam(required = false) String respuesta, @RequestParam(required = false) String id, HttpSession session) {
        String username = UtilidadesSesion.getUserName(session);
        Usuario usuario = (username.length()>0) ? usuariosRepository.findTopByUsername(username) : new Usuario();
        Reserva reserva = (Reserva) session.getAttribute("reserva");
        if (id==null) return new ModelAndView("redirect:/");
        ModelAndView modelAndView = new ModelAndView("alojamiento");
        modelAndView.addObject("alojamiento", alojamientosService.buscarPorId(id));
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        modelAndView.addObject("reserva", reserva==null?new Reserva():reserva);
        modelAndView.addObject("comentarios", comentariosService.obtenerComentariosActivosAlojamiento(id, usuario.getId_usuario()));
        modelAndView.addObject("comentariopropio", comentariosService.obtenerComentarioUsuario(usuario, id));
        return modelAndView;
    }

    @GetMapping("/admin/alojamientos")
    public ModelAndView administrarAlojamientos(@RequestParam(required = false) String respuesta, @RequestParam(required = false) String q) {
        List<Alojamiento> listaAlojamientos = (q==null) ? alojamientosService.obtenerAlojamientos() : alojamientosService.buscarAlojamientos(q);
        ModelAndView modelAndView = new ModelAndView("admin/alojamientos");
        modelAndView.addObject("alojamientos", listaAlojamientos);
        modelAndView.addObject("alojamientoedit", new Alojamiento());
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        modelAndView.addObject("query", (q==null) ? "" : q);
        return modelAndView;
    }
    @PostMapping("/admin/alojamientos")
    public ModelAndView editarAlojamiento(@RequestParam(required = false) String respuesta, @RequestParam String id_alojamiento) {
        List<Alojamiento> listaAlojamientos = alojamientosService.obtenerAlojamientos();
        ModelAndView modelAndView = new ModelAndView("admin/alojamientos");
        modelAndView.addObject("alojamientos", listaAlojamientos);
        modelAndView.addObject("alojamientoedit", alojamientosService.buscarPorId(id_alojamiento));
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        return modelAndView;
    }

    @PostMapping("/admin/agregaralojamiento")
    public String agregarAlojamiento(@ModelAttribute(name = "alojamiento") Alojamiento nuevoalojamiento){
        boolean esNuevo = nuevoalojamiento.getId_alojamiento()==0;
        Alojamiento oldalojamiento = (esNuevo) ? nuevoalojamiento : alojamientosService.buscarPorId(Integer.toString(nuevoalojamiento.getId_alojamiento()));
        oldalojamiento.setNombre(nuevoalojamiento.getNombre());
        oldalojamiento.setUbicacion(nuevoalojamiento.getUbicacion());
        oldalojamiento.setDireccion(nuevoalojamiento.getDireccion());
        oldalojamiento.setMapa(nuevoalojamiento.getMapa());
        oldalojamiento.setImagen(nuevoalojamiento.getImagen());
        oldalojamiento.setPrecio(nuevoalojamiento.getPrecio());
        oldalojamiento.setTipo(nuevoalojamiento.getTipo());
        oldalojamiento.setHuespedes(nuevoalojamiento.getHuespedes());
        oldalojamiento.setHabitaciones(nuevoalojamiento.getHabitaciones());
        oldalojamiento.setActivo(true);
        return (alojamientosService.guardarAlojamiento(oldalojamiento)!=null) ? ((esNuevo) ? "redirect:/admin/alojamientos?respuesta=agregado" : "redirect:/admin/alojamientos?respuesta=editado") : "redirect:/admin/alojamientos?respuesta=error";
    }

    @PostMapping("/admin/deshabilitaralojamiento")
    public String deshabilitarAlojamiento(@RequestParam String id_alojamiento){
        alojamientosService.deshabilitarPorId(id_alojamiento);
        return "redirect:/admin/alojamientos?respuesta=deshabilitado";
    }
}
