package com.example.reservamiento.controller;

import java.util.Collections;
import javax.servlet.http.HttpServletRequest;

import com.example.reservamiento.model.Usuario;
import com.example.reservamiento.security.SignupRequest;
import com.example.reservamiento.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    @Autowired
    UsuariosService usuariosService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public Model login(Model model, @RequestParam(name = "respuesta", required = false) String respuesta) {
        if (respuesta != null) model.addAttribute("respuesta", respuesta);
        return model;
    }

    @GetMapping("/register")
    public Model register(Model model) {
        model.addAttribute("register", new SignupRequest());
        return model;
    }
    @RequestMapping("/register")
    public String register(@ModelAttribute(name = "register") SignupRequest register) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam(name = "respuesta", required = false) String respuesta,
                           @ModelAttribute(name = "register") SignupRequest register,
                           Model model, HttpServletRequest request) {
        Usuario usuario = new Usuario(register.getNombre(), register.getApellidos(), register.getEmail(), register.getUsername(), register.getTelefono(), register.getGenero());


        if (usuariosService.existeUsuario(usuario)) {
            model.addAttribute("respuesta", "usuarioexiste");
            return "register";
        }
        else if (usuariosService.existeCorreo(usuario)) {
                model.addAttribute("respuesta", "correoexiste");
                return "register";
        } else {
            usuario.setPassword(bCryptPasswordEncoder.encode(register.getPassword()));
            usuariosService.guardarUsuario(usuario);

            //FORZAR A LOGUEARSE
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(new User(usuario.getUsername(), usuario.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().toString()))),
                    usuario.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().toString())));

            authToken.setDetails(new WebAuthenticationDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

            return "redirect:/";
        }
    }
}