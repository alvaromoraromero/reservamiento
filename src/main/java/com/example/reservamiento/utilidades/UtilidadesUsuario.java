package com.example.reservamiento.utilidades;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.ERole;
import com.example.reservamiento.model.Usuario;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UtilidadesUsuario {

    public UtilidadesUsuario() {
    }

    private static Faker faker = new Faker(Locale.forLanguageTag("es"));

    public static Usuario crearUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNombre(faker.name().firstName());
        usuario.setApellidos(faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setUsername(faker.name().username());
        usuario.setPassword("1234");
        usuario.setTelefono(faker.phoneNumber().phoneNumber());
        usuario.setGenero("0");
        usuario.setRol(ERole.USUARIO);
        usuario.setFecha_registro();
        usuario.setActivo(true);
        return usuario;
    }

    public static List<Usuario> crearUsuario(int numElementos){
        List<Usuario> usuarios = new ArrayList<>();
        for(int i = 0; i <= numElementos; i ++){
            Usuario a = crearUsuario();
            usuarios.add(a);
        }
        return usuarios;
    }
}
