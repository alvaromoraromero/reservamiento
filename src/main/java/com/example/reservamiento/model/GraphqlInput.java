package com.example.reservamiento.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

public class GraphqlInput {
    @Data
    @AllArgsConstructor
    public class AlojamientoInput {
        private String nombre;
        private String ubicacion;
        private String direccion;
        private String mapa;
        private String imagen;
        private Integer precio;
        private String tipo;
        private Integer huespedes;
        private Integer habitaciones;
    }

    @Data
    @AllArgsConstructor
    public class ComentarioInput {
        private Integer id_usuario;
        private Integer id_alojamiento;
        private Integer estrellas;
        private String titulo;
        private String descripcion;
    }

    @Data
    @AllArgsConstructor
    public class UsuarioInput {
        private String nombre;
        private String apellidos;
        private String email;
        private String username;
        private String password;
        private String telefono;
        private String genero;
        private LocalDate fecha_registro;
        private ERole rol;
    }


    @Data
    @AllArgsConstructor
    public class ReservaInput {
        private Integer id_usuario;
        private Integer id_alojamiento;
        private LocalDate fecha_inicio;
        private LocalDate fecha_fin;
        private Integer personas;
        private Boolean pagado;
    }
}
