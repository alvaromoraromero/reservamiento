package com.example.reservamiento.utilidades;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Reserva;
import com.example.reservamiento.model.Usuario;
import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilidadesReserva {

    public UtilidadesReserva() {
    }

    private static Faker faker = new Faker(Locale.forLanguageTag("es"));

    public static Reserva crearReserva(){
        Reserva reserva = new Reserva();
        reserva.setUsuario(new Usuario());
        reserva.setAlojamiento(new Alojamiento());
        reserva.setFecha_inicio(LocalDate.now().plusDays(1));
        reserva.setFecha_fin(LocalDate.now().plusDays(2));
        reserva.setPersonas(faker.number().numberBetween(1, 8));
        reserva.setObservaciones(faker.lorem().paragraph());
        reserva.setFecha_reserva();
        reserva.setHabitaciones(faker.number().numberBetween(10, 100));
        reserva.setImporte(faker.number().numberBetween(10, 999));
        reserva.setPagado(false);
        reserva.setActivo(true);
        return reserva;
    }

    public static List<Reserva> crearReserva(int numElementos){
        List<Reserva> reservas = new ArrayList<>();
        for(int i = 0; i <= numElementos; i ++){
            Reserva r = crearReserva();
            reservas.add(r);
        }
        return reservas;
    }
}
