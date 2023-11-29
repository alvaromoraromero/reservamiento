package com.example.reservamiento.controller;

import org.springframework.security.core.userdetails.User;
import com.example.reservamiento.model.Usuario;
import com.example.reservamiento.repository.UsuariosRepository;
import com.example.reservamiento.security.SignupRequest;
import com.example.reservamiento.utilidades.UtilidadesSesion;
import com.example.reservamiento.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
public class UsuariosController {

    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/cuenta")
    public Model cuenta(@RequestParam(name = "respuesta", required = false) String respuesta, Model model, HttpSession session) {
        if (respuesta != null) model.addAttribute("respuesta", respuesta);
        model.addAttribute("cuenta", usuariosRepository.findTopByUsername(UtilidadesSesion.getUserName(session)));
        return model;
    }

    @PostMapping("/cuenta")
    public String cuenta(@ModelAttribute(name = "cuenta") SignupRequest cuenta,
                       Model model, HttpServletRequest request, HttpSession session) {
        Usuario nuevo = new Usuario(cuenta.getNombre(), cuenta.getApellidos(), cuenta.getEmail(), cuenta.getUsername(), cuenta.getTelefono(), cuenta.getGenero());
        Usuario old = usuariosRepository.findTopByUsername(UtilidadesSesion.getUserName(session));
        nuevo.setRol(old.getRol());

        if (!nuevo.getUsername().equalsIgnoreCase(old.getUsername()) && usuariosService.existeUsuario(nuevo)) {
            model.addAttribute("respuesta", "usuarioexiste");
            return "cuenta";
        }
        else if (!nuevo.getEmail().equalsIgnoreCase(old.getEmail()) && usuariosService.existeCorreo(nuevo)) {
            model.addAttribute("respuesta", "correoexiste");
            return "cuenta";
        } else {
            if (cuenta.getPassword().length()>0)
                nuevo.setPassword(bCryptPasswordEncoder.encode(cuenta.getPassword()));
            else
                nuevo.setPassword(old.getPassword());

            usuariosService.actualizarUsuario(nuevo, old.getUsername());

            //FORZAR A LOGUEARSE
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(new User(nuevo.getUsername(), nuevo.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(nuevo.getRol().toString()))),
                    nuevo.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(nuevo.getRol().toString())));

            authToken.setDetails(new WebAuthenticationDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

            return "redirect:/cuenta?respuesta=correcto";
        }
    }

    @GetMapping("/admin/usuarios")
    public ModelAndView administrarUsuarios(@RequestParam(required = false) String respuesta, @RequestParam(required = false) String q) {
        List<Usuario> listaUsuarios = (q==null) ? usuariosService.obtenerUsuarios() : usuariosService.buscarUsuarios(q);
        ModelAndView modelAndView = new ModelAndView("admin/usuarios");
        modelAndView.addObject("usuarios", listaUsuarios);
        modelAndView.addObject("usuarioedit", new Usuario());
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        modelAndView.addObject("query", (q==null) ? "" : q);
        return modelAndView;
    }
    @PostMapping("/admin/usuarios")
    public ModelAndView editarUsuario(@RequestParam(required = false) String respuesta, @RequestParam String id_usuario) {
        List<Usuario> listaUsuarios = usuariosService.obtenerUsuarios();
        ModelAndView modelAndView = new ModelAndView("admin/usuarios");
        modelAndView.addObject("usuarios", listaUsuarios);
        modelAndView.addObject("usuarioedit", usuariosService.buscarPorId(id_usuario));
        if (respuesta!=null) modelAndView.addObject("respuesta", respuesta);
        return modelAndView;
    }

    @PostMapping("/admin/agregarusuario")
    public String agregarUsuario(@ModelAttribute(name = "alojamiento") Usuario nuevousuario){
        boolean esNuevo = nuevousuario.getId_usuario()==0;
        Usuario oldusuario = (esNuevo) ? nuevousuario : usuariosService.buscarPorId(Integer.toString(nuevousuario.getId_usuario()));
        oldusuario.setNombre(nuevousuario.getNombre());
        oldusuario.setApellidos(nuevousuario.getApellidos());
        oldusuario.setEmail(nuevousuario.getEmail());
        oldusuario.setUsername(nuevousuario.getUsername());
        if (nuevousuario.getPassword().length()>0) oldusuario.setPassword(bCryptPasswordEncoder.encode(nuevousuario.getPassword()));
        oldusuario.setTelefono(nuevousuario.getTelefono().length()>0 ? nuevousuario.getTelefono() : null);
        oldusuario.setGenero(nuevousuario.getGenero());
        if (esNuevo) oldusuario.setFecha_registro();
        oldusuario.setRol(nuevousuario.getRol());
        oldusuario.setActivo(true);
        return (usuariosService.guardarUsuario(oldusuario)!=null) ? ((esNuevo) ? "redirect:/admin/usuarios?respuesta=agregado" : "redirect:/admin/usuarios?respuesta=editado") : "redirect:/admin/usuarios?respuesta=error";
    }

    @PostMapping("/admin/deshabilitarusuario")
    public String deshabilitarAlojamiento(@RequestParam String id_usuario){
        usuariosService.desactivarUsuario(id_usuario);
        return "redirect:/admin/usuarios?respuesta=deshabilitado";
    }
}
