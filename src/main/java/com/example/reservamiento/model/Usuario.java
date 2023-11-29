package com.example.reservamiento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.example.reservamiento.model.ERole.USUARIO;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id_usuario;

    @NotBlank
    @Size(max = 30)
    @Column(name = "nombre")
    private String nombre;

    @NotBlank
    @Size(max = 30)
    @Column(name = "apellidos")
    private String apellidos;

    @NotBlank
    @Size(max = 30)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Column(name = "telefono")
    @Size(max = 9)
    private String telefono;

    @NotBlank
    @Size(max = 1)
    @Column(name = "genero")
    private String genero;

    @Column(name = "fecha_registro")
    private LocalDate fecha_registro;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "rol")
    @Enumerated(EnumType.ORDINAL)
    private ERole rol;

    @Override
    public boolean equals(Object o) {
        return o!=null ? this.id_usuario==((Usuario)o).getId_usuario() : false;
    }

    @Override
    public int hashCode() {
        return id_usuario;
    }

    public Usuario() {
        this.rol = USUARIO;
    }

    public Usuario(String nombre, String apellidos, String email, String username, String telefono, String genero) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.username = username;
        this.telefono = telefono.length()==0?null:telefono;
        this.genero = genero;
        this.setFecha_registro();
        this.activo = true;
        this.rol = USUARIO;
    }

    public void setFecha_registro() {
        this.fecha_registro = LocalDate.now();
    }

    @Override
    public String toString() {
        return "USUARIO:" +
                "\n id: " + id_usuario +
                "\n nombre: " + nombre +
                "\n apellidos: " + apellidos +
                "\n email: " + email +
                "\n username: " + username +
                "\n password: " + password +
                "\n teléfono: " + telefono +
                "\n genero: " + genero +
                "\n fecha_registro: " + fecha_registro +
                "\n activo: " + activo +
                "\n rol: " + rol;
    }
}
