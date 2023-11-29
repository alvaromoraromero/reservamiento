package com.example.reservamiento.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class SignupRequest {
    @NotBlank
    @Size(max = 30)
    private String nombre;
    @NotBlank
    @Size(max = 30)
    private String apellidos;
    @NotBlank
    @Size(min = 3, max = 30)
    private String username;
    @NotBlank
    @Size(max = 30)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    @Size(max = 9)
    private String telefono;

    @Size(max = 1)
    private String genero="";
}
