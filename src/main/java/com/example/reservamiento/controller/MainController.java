package com.example.reservamiento.controller;

import com.example.reservamiento.model.Usuario;
import com.example.reservamiento.repository.UsuariosRepository;
import com.example.reservamiento.utilidades.UtilidadesSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    UsuariosRepository usuariosRepository;
    @RequestMapping("/index")
    public String index() {
        return "redirect:/";
    }

    @GetMapping("/contacto")
    public ModelAndView contacto(HttpSession session) {
        String username = UtilidadesSesion.getUserName(session);
        Usuario usuario = username.equals("") ? new Usuario() : usuariosRepository.findTopByUsername(username);
        ModelAndView modelAndView = new ModelAndView("contacto");
        modelAndView.addObject("contacto", usuario);
        return modelAndView;
    }

    @RequestMapping("/admin")
    public ModelAndView pantallaAdministracion() {
        return new ModelAndView("admin");
    }

    @RequestMapping("/privacidad")
    public String privacidad() {
        return "privacidad";
    }
}
