package com.example.reservamiento.utilidades;

import com.example.reservamiento.model.ERole;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpSession;
import java.util.Collections;

public class UtilidadesSesion {
    public static boolean hayUsuarioLogeado(HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");

        return securityContext != null
                && securityContext.getAuthentication() != null
                && securityContext.getAuthentication().getPrincipal() !=null;
    }


    public static ERole getRolUsuarioLogueado(HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return ERole.valueOf((Collections.singletonList(securityContext.getAuthentication().getAuthorities()).get(0).toString().replace("[","").replace("]","")));
    }

    public static String getUserName(HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return (securityContext==null) ? "" : ((User)securityContext.getAuthentication().getPrincipal()).getUsername();
    }
}
