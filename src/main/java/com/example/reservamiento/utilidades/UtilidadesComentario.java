package com.example.reservamiento.utilidades;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Comentario;
import com.example.reservamiento.model.Usuario;
import com.github.javafaker.Faker;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UtilidadesComentario {

    public UtilidadesComentario() {
    }

    private static Faker faker = new Faker(Locale.forLanguageTag("es"));

    public static Comentario crearComentario(){
        Comentario comentario = new Comentario();
        comentario.setUsuario(new Usuario());
        comentario.setAlojamiento(new Alojamiento());
        comentario.setEstrellas(faker.number().numberBetween(1, 5));
        comentario.setTitulo(faker.lorem().sentence());
        comentario.setDescripcion(faker.lorem().paragraph());
        comentario.setFecha_comentario();
        comentario.setMostrar(true);
        return comentario;
    }

    public static List<Comentario> crearComentario(int numElementos){
        List<Comentario> comentarios = new ArrayList<>();
        for(int i = 0; i <= numElementos; i ++){
            Comentario c = crearComentario();
            comentarios.add(c);
        }
        return comentarios;
    }
}
