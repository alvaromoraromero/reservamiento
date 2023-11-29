package com.example.reservamiento.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="reservas")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Reserva {

    @Id
    @Column(name = "id_reserva")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id_reserva;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name = "id_alojamiento")
    private Alojamiento alojamiento;
    @Column(name = "fecha_inicio")
    private LocalDate fecha_inicio;
    @Column(name = "fecha_fin")
    private LocalDate fecha_fin;
    @Column(name = "personas")
    private int personas;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "fecha_reserva")
    private LocalDate fecha_reserva;
    @Column(name = "habitaciones")
    private int habitaciones;
    @Column(name = "importe")
    private int importe;
    @Column(name = "pagado")
    private boolean pagado;
    @Column(name = "activo")
    private boolean activo;

    @Override
    public boolean equals(Object o) {
        return (o!=null) ? this.id_reserva==((Reserva)o).getId_reserva() : false;
    }

    @Override
    public int hashCode() {
        return id_reserva;
    }

    public Reserva(String fecha_inicio, String fecha_fin, int personas) {
        this.setFecha_inicio(fecha_inicio);
        this.setFecha_fin(fecha_fin);
        this.personas = personas;
        this.setFecha_reserva();
        this.pagado = false;
        this.activo = true;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = LocalDate.parse(fecha_inicio);
    }
    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = LocalDate.parse(fecha_fin);
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setFecha_reserva() {
        this.fecha_reserva = (this.fecha_reserva==null) ? LocalDate.now() : this.fecha_reserva;
    }

    @Override
    public String toString() {
        return "RESERVA:" +
                "\n id: " + id_reserva +
                "\n usuario: " + usuario.getId_usuario() +
                "\n alojamiento: " + alojamiento.getId_alojamiento() +
                "\n fecha_inicio: " + fecha_inicio +
                "\n fecha_fin: " + fecha_fin +
                "\n personas: " + personas +
                "\n observaciones: " + observaciones +
                "\n fecha_reserva: " + fecha_reserva +
                "\n habitaciones: " + habitaciones +
                "\n importe: " + importe +
                "\n pagado: " + pagado +
                "\n activo: " + activo;
    }
}
