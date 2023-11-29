package com.example.reservamiento.controller;

import com.example.reservamiento.model.*;
import com.example.reservamiento.service.AlojamientosService;
import com.example.reservamiento.service.ComentariosService;
import com.example.reservamiento.service.ReservasService;
import com.example.reservamiento.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.List;
@RestController
public class ApiController {
    @Autowired
    private AlojamientosService alojamientosService;
    @Autowired
    private ComentariosService comentariosService;
    @Autowired
    private ReservasService reservasService;
    @Autowired
    private UsuariosService usuariosService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    // INICIO ALOJAMIENTOS
    @GetMapping("/listAlojamientos")
    @QueryMapping
    public List<Alojamiento> allAlojamientos(){
        return alojamientosService.obtenerAlojamientos();
    }
    @GetMapping("/alojamiento/{id}")
    @QueryMapping
    public Alojamiento findAlojamientoByID(@PathVariable @Argument String id){
        return alojamientosService.buscarPorId(id);
    }
    @GetMapping("/alojamiento/buscar?destino={destino}&fechainicio={fechainicio}&fechafin={fechafin}&personas={personas}")
    @QueryMapping
    public List<Alojamiento> buscadorAlojamientos(@PathVariable @Argument String destino, @PathVariable @Argument String fechainicio, @PathVariable @Argument String fechafin, @PathVariable @Argument int personas) {
        return alojamientosService.buscarAlojamientos(destino, fechainicio, fechafin, personas);
    }
    @PostMapping("/alojamiento/guardar")
    @SchemaMapping(typeName = "Mutation", value = "guardarAlojamiento")
    public String guardarAlojamiento(@RequestBody @Argument(name = "alojamiento") GraphqlInput.AlojamientoInput input,
                                     @RequestBody(required = false) @Argument(name = "id") String id) {
        Alojamiento oldalojamiento = new Alojamiento();
        boolean esNuevo = id==null;
        if (!esNuevo) {
            Alojamiento nuevoalojamiento = alojamientosService.buscarPorId(id);
            if (nuevoalojamiento !=null)
                oldalojamiento = nuevoalojamiento;
            else
                return "ERROR: No se encuentra ningún alojamiento con la ID: " + id;
        }
        if (input!=null) {
            if (input.getNombre() != null) oldalojamiento.setNombre(input.getNombre());
            if (input.getUbicacion() != null) oldalojamiento.setUbicacion(input.getUbicacion());
            if (input.getDireccion() != null) oldalojamiento.setDireccion(input.getDireccion());
            if (input.getMapa() != null) oldalojamiento.setMapa(input.getMapa());
            if (input.getImagen() != null) oldalojamiento.setImagen(input.getImagen());
            if (input.getPrecio() != null) oldalojamiento.setPrecio(input.getPrecio());
            if (input.getTipo() != null) oldalojamiento.setTipo(input.getTipo());
            if (input.getHuespedes() != null) oldalojamiento.setHuespedes(input.getHuespedes());
            if (input.getHabitaciones() != null) oldalojamiento.setHabitaciones(input.getHabitaciones());
        }
        oldalojamiento.setActivo(true);
        boolean guardado = alojamientosService.guardarAlojamiento(oldalojamiento)!=null;
        if (guardado && esNuevo)
            return "Alojamiento creado correctamente";
        else if (guardado)
            return "Alojamiento editado correctamente";
        else
            return "ERROR: No se han guardado los cambios";
    }

    @DeleteMapping("/alojamiento/deshabilitar/{id}")
    @MutationMapping
    public String deshabilitarAlojamiento(@PathVariable @Argument String id) {
        alojamientosService.deshabilitarPorId(id);
        return "Alojamiento deshabilitado correctamente";
    }
    @GetMapping("/alojamiento/habilitar/{id}")
    @MutationMapping
    public String habilitarAlojamiento(@PathVariable @Argument String id) {
        return guardarAlojamiento(null, id);
    }
    // FIN ALOJAMIENTOS


    // INICIO COMENTARIOS
    @GetMapping("/listComentarios")
    @QueryMapping
    public List<Comentario> allComentarios(){
        return comentariosService.obtenerComentarios();
    }
    @GetMapping("/comentario/{id}")
    @QueryMapping
    public Comentario findComentarioByID(@PathVariable @Argument String id){
        return comentariosService.obtenerPorId(id);
    }
    @PostMapping("/comentario/guardar")
    @SchemaMapping(typeName = "Mutation", value = "guardarComentario")
    public String guardarComentario(@RequestBody @Argument(name = "comentario") GraphqlInput.ComentarioInput input,
                                     @RequestBody(required = false) @Argument(name = "id") String id) {
        Comentario oldcomentario = new Comentario();
        boolean esNuevo = id==null;
        if (!esNuevo) {
            Comentario nuevocomentario = comentariosService.obtenerPorId(id);
            if (nuevocomentario !=null)
                oldcomentario = nuevocomentario;
            else
                return "ERROR: No se encuentra ningún comentario con la ID: " + id;
        }
        if (input!=null) {
            if (input.getId_usuario() != null) oldcomentario.setUsuario(usuariosService.buscarPorId(input.getId_usuario().toString()));
            if (input.getId_alojamiento() != null) oldcomentario.setAlojamiento(alojamientosService.buscarPorId(input.getId_alojamiento().toString()));
            if (input.getEstrellas() != null) oldcomentario.setEstrellas(input.getEstrellas());
            if (input.getTitulo() != null) oldcomentario.setTitulo(input.getTitulo());
            if (input.getDescripcion() != null) oldcomentario.setDescripcion(input.getDescripcion());
        }
        oldcomentario.setFecha_comentario();
        oldcomentario.setMostrar(true);
        boolean guardado = comentariosService.guardarComentario(oldcomentario)!=null;
        if (guardado && esNuevo)
            return "Comentario creado correctamente";
        else if (guardado)
            return "Comentario editado correctamente";
        else
            return "ERROR: No se han guardado los cambios";
    }

    @DeleteMapping("/comentario/ocultar/{id}")
    @MutationMapping
    public String ocultarComentario(@PathVariable @Argument String id) {
        comentariosService.ocultarPorId(id);
        return "Comentario ocultado correctamente";
    }
    @GetMapping("/comentario/mostrar/{id}")
    @MutationMapping
    public String mostrarComentario(@PathVariable @Argument String id) {
        return guardarComentario(null, id);
    }
    // FIN COMENTARIOS


    // INICIO RESERVAS
    @GetMapping("/listReservas")
    @QueryMapping
    public List<Reserva> allReservas(){
        return reservasService.obtenerReservas();
    }
    @GetMapping("/reserva/{id}")
    @QueryMapping
    public Reserva findReservaByID(@PathVariable @Argument String id){
        return reservasService.obtenerPorId(id);
    }
    @SchemaMapping(typeName = "Mutation", value = "guardarReserva")
    public String guardarReserva(@RequestBody @Argument(name = "reserva") GraphqlInput.ReservaInput input,
                                 @RequestBody(required = false) @Argument(name = "id") String id) {
        Reserva oldreserva = new Reserva();
        boolean esNuevo = id==null;
        if (!esNuevo) {
            Reserva nuevareserva = reservasService.obtenerPorId(id);
            if (nuevareserva !=null)
                oldreserva = nuevareserva;
            else
                return "ERROR: No se encuentra ninguna reserva con la ID: " + id;
        }
        Alojamiento alojamiento = (esNuevo) ? alojamientosService.buscarPorId(input.getId_alojamiento().toString()) : oldreserva.getAlojamiento();
        if (input!=null) {
            if (input.getId_usuario() != null)
                oldreserva.setUsuario(usuariosService.buscarPorId(input.getId_usuario().toString()));
            if (input.getId_alojamiento() != null) oldreserva.setAlojamiento(alojamiento);
            if (input.getFecha_inicio() != null) oldreserva.setFecha_inicio(input.getFecha_inicio());
            if (input.getFecha_fin() != null) oldreserva.setFecha_fin(input.getFecha_fin().toString());
            oldreserva.setObservaciones("Modificado por un administrador");
            if (esNuevo) oldreserva.setFecha_reserva();
            if (input.getPersonas() != null) {
                oldreserva.setPersonas(input.getPersonas());
                oldreserva.setHabitaciones((int) Math.ceil((double) input.getPersonas() / alojamiento.getHuespedes()));
                oldreserva.setImporte(alojamiento.getPrecio() * oldreserva.getHabitaciones() * (int) (Duration.between(oldreserva.getFecha_inicio().atStartOfDay(), oldreserva.getFecha_fin().atStartOfDay()).toDays()));
            }
            oldreserva.setPagado((input.getPagado() != null) ? input.getPagado() : false);
        }
        oldreserva.setActivo(true);
        boolean guardado = reservasService.guardarReserva(oldreserva)!=null;
        if (guardado && esNuevo)
            return "Reserva creada correctamente";
        else if (guardado)
            return "Reserva editada correctamente";
        else
            return "ERROR: No se han guardado los cambios";
    }
    @DeleteMapping("/reserva/cancelar/{id}")
    @MutationMapping
    public String cancelarReserva(@PathVariable @Argument String id) {
        reservasService.cancelarPorId(id);
        return "Reserva cancelada correctamente";
    }
    @GetMapping("/reserva/habilitar/{id}")
    @MutationMapping
    public String habilitarReserva(@PathVariable @Argument String id) {
        return guardarReserva(null, id);
    }
    // FIN RESERVAS


    // INICIO USUARIOS
    @GetMapping("/listUsuarios")
    @QueryMapping
    public List<Usuario> allUsuarios(){
        return usuariosService.obtenerUsuarios();
    }
    @GetMapping("/usuario/{id}")
    @QueryMapping
    public Usuario findUsuarioByID(@PathVariable @Argument String id){
        return usuariosService.buscarPorId(id);
    }
    @PostMapping("/usuario/guardar")
    @SchemaMapping(typeName = "Mutation", value = "guardarUsuario")
    public String guardarUsuario(@RequestBody @Argument(name = "usuario") GraphqlInput.UsuarioInput input,
                                     @RequestBody(required = false) @Argument(name = "id") String id) {
        Usuario oldusuario = new Usuario();
        boolean esNuevo = id==null;
        if (!esNuevo) {
            Usuario nuevousuario = usuariosService.buscarPorId(id);
            if (nuevousuario !=null)
                oldusuario = nuevousuario;
            else
                return "ERROR: No se encuentra ningún usuario con la ID: " + id;
        }
        if (input!=null) {
            if (input.getNombre() != null) oldusuario.setNombre(input.getNombre());
            if (input.getApellidos() != null) oldusuario.setApellidos(input.getApellidos());
            if (input.getEmail() != null) oldusuario.setEmail(input.getEmail());
            if (input.getUsername() != null) oldusuario.setUsername(input.getUsername());
            if (input.getPassword() != null) oldusuario.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));
            if (input.getTelefono() != null) oldusuario.setTelefono(input.getTelefono());
            if (input.getGenero() != null) oldusuario.setGenero(input.getGenero());
            oldusuario.setFecha_registro();
            if (input.getRol() != null) oldusuario.setRol(input.getRol());
        }
        oldusuario.setActivo(true);
        boolean guardado = usuariosService.guardarUsuario(oldusuario)!=null;
        if (guardado && esNuevo)
            return "Usuario creado correctamente";
        else if (guardado)
            return "Usuario editado correctamente";
        else
            return "ERROR: No ne han guardado los cambios";
    }
    @DeleteMapping("/usuario/desactivar/{id}")
    @MutationMapping
    public String desactivarUsuario(@PathVariable @Argument String id) {
        usuariosService.desactivarUsuario(id);
        return "Usuario desactivado correctamente";
    }
    @DeleteMapping("/usuario/reactivar/{id}")
    @MutationMapping
    public String reactivarUsuario(@PathVariable @Argument String id) {
        return guardarUsuario(null, id);
    }
    // FIN USUARIOS
}
