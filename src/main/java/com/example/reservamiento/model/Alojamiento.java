package com.example.reservamiento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "alojamientos")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Alojamiento {
    @Id
    @Column(name = "id_alojamiento")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id_alojamiento;
    @NotBlank
    @Size(max = 30)
    @Column(name = "nombre")
    private String nombre;
    @NotBlank
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;

    @NotBlank
    @Column(name = "mapa")
    private String mapa;
    @NotBlank
    @Column(name = "imagen")
    private String imagen;
    @NotBlank
    @Size(max = 30)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Column(name = "precio")
    private int precio;
    @NotBlank
    @Size(max = 15)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "huespedes")
    private int huespedes;
    @Column(name = "habitaciones")
    private int habitaciones;
    @Column(name = "activo")
    private boolean activo;

    @Override
    public boolean equals(Object o) {
        return (o!=null) ? this.id_alojamiento==((Alojamiento)o).getId_alojamiento() : false;
    }

    @Override
    public int hashCode() {
        return id_alojamiento;
    }

    @Override
    public String toString() {
        return "ALOJAMIENTO:" +
                "\n id: " + id_alojamiento +
                "\n nombre: " + nombre +
                "\n dirección: " + direccion +
                "\n ubicación: " + ubicacion +
                "\n precio: " + precio +
                "\n tipo: " + tipo +
                "\n huéspedes: " + huespedes +
                "\n habitaciones: " + habitaciones +
                "\n activo: " + activo;
    }
}
