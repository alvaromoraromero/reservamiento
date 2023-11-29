package com.example.reservamiento.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="comentarios")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Comentario {

    @Id
    @Column(name = "id_comentario")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id_comentario;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name = "id_alojamiento")
    private Alojamiento alojamiento;
    @Min(1)
    @Max(5)
    @Column(name = "estrellas")
    private int estrellas;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_comentario")
    private LocalDateTime fecha_comentario;
    @Column(name = "mostrar")
    private boolean mostrar;

    @Override
    public boolean equals(Object o) {
        return (o!=null) ? this.id_comentario==((Comentario)o).getId_comentario() : false;
    }

    @Override
    public int hashCode() {
        return id_comentario;
    }

    public Comentario(int estrellas,String titulo, String descripcion) {
        this.estrellas = estrellas;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.setFecha_comentario();
        this.mostrar = true;
    }

    public void setFecha_comentario() {
        this.fecha_comentario = (this.fecha_comentario==null) ? LocalDateTime.now() : this.fecha_comentario;
    }

    @Override
    public String toString() {
        return "COMENTARIO:" +
                "\n id: " + id_comentario +
                "\n usuario: " + usuario.getId_usuario() +
                "\n alojamiento: " + alojamiento.getId_alojamiento() +
                "\n estrellas: " + estrellas +
                "\n titulo: " + titulo +
                "\n descripción: " + descripcion +
                "\n fecha_comentario: " + fecha_comentario +
                "\n mostrar: " + mostrar;
    }
}
