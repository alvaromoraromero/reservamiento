package com.example.reservamiento.controller;


import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Comentario;
import com.example.reservamiento.repository.UsuariosRepository;
import com.example.reservamiento.service.AlojamientosService;
import com.example.reservamiento.service.ComentariosService;
import com.example.reservamiento.utilidades.UtilidadesSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ComentariosController {
    @Autowired
    private AlojamientosService alojamientosService;
    @Autowired
    private ComentariosService comentariosService;
    @Autowired
    private UsuariosRepository usuariosRepository;

    @PostMapping("/alojamiento")
    public ModelAndView publicarComentario(@ModelAttribute Comentario comentario, @RequestParam String id_alojamiento, HttpSession session){
        String username = UtilidadesSesion.getUserName(session);
        if (username.length()!=0) {
            comentario.setUsuario(usuariosRepository.findTopByUsername(username));
            comentario.setAlojamiento(alojamientosService.buscarPorId(id_alojamiento));
            comentario.setFecha_comentario();
            comentario.setMostrar(true);
            comentariosService.guardarComentario(comentario);
        }
        return new ModelAndView("redirect:/alojamiento?respuesta=publicado&id=" + id_alojamiento);
    }
    @PostMapping("/eliminarcomentario")
    public String eliminarComentario(@RequestParam String id_comentario, @RequestParam String id_alojamiento){
        comentariosService.ocultarPorId(id_comentario);
        return "redirect:/alojamiento?respuesta=eliminado&id=" + id_alojamiento;
    }

    @GetMapping("/admin/comentarios")
    public ModelAndView administrarComentarios(@RequestParam(required = false) String respuesta, @RequestParam(required = false) String q) {
        List<Comentario> listaComentarios = (q==null) ? comentariosService.obtenerComentarios() : comentariosService.buscarComentarios(q);
        ModelAndView modelAndView = new ModelAndView("admin/comentarios");
        modelAndView.addObject("comentarios", listaComentarios);
        modelAndView.addObject("comentarioedit", new Comentario());
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        modelAndView.addObject("query", (q==null) ? "" : q);
        return modelAndView;
    }
    @PostMapping("/admin/comentarios")
    public ModelAndView editarComentario(@RequestParam(required = false) String respuesta, @RequestParam String id_comentario) {
        List<Comentario> listaComentarios = comentariosService.obtenerComentarios();
        ModelAndView modelAndView = new ModelAndView("admin/comentarios");
        modelAndView.addObject("comentarios", listaComentarios);
        modelAndView.addObject("comentarioedit", comentariosService.obtenerPorId(id_comentario));
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        return modelAndView;
    }

    @PostMapping("/admin/agregarcomentario")
    public String agregarComentario(@ModelAttribute(name = "comentario") Comentario nuevocomentario, @RequestParam String username, @RequestParam String id_alojamiento){
        if (!usuariosRepository.existsByUsername(username)) return "redirect:/admin/comentarios?respuesta=errorusuario";
        Alojamiento alojamiento = alojamientosService.buscarPorId(id_alojamiento);
        if (alojamiento == null) return "redirect:/admin/comentarios?respuesta=erroralojamiento";
        boolean esNuevo = nuevocomentario.getId_comentario()==0;
        Comentario oldcomentario = (esNuevo) ? nuevocomentario : comentariosService.obtenerPorId(Integer.toString(nuevocomentario.getId_comentario()));
        oldcomentario.setUsuario(usuariosRepository.findTopByUsername(username));
        oldcomentario.setAlojamiento(alojamiento);
        oldcomentario.setEstrellas(nuevocomentario.getEstrellas());
        oldcomentario.setTitulo(nuevocomentario.getTitulo());
        oldcomentario.setDescripcion(nuevocomentario.getDescripcion());
        oldcomentario.setFecha_comentario();
        oldcomentario.setMostrar(true);
        return (comentariosService.guardarComentario(oldcomentario)!=null) ? ((esNuevo) ? "redirect:/admin/comentarios?respuesta=agregado" : "redirect:/admin/comentarios?respuesta=editado") : "redirect:/admin/comentarios?respuesta=error";
    }

    @PostMapping("/admin/ocultarcomentario")
    public String ocultarComentario(@RequestParam String id_comentario){
        comentariosService.ocultarPorId(id_comentario);
        return "redirect:/admin/comentarios?respuesta=ocultado";
    }
}
