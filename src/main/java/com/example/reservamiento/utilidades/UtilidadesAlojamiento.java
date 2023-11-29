package com.example.reservamiento.utilidades;

import com.example.reservamiento.model.Alojamiento;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UtilidadesAlojamiento {

    public UtilidadesAlojamiento() {
    }

    private static Faker faker = new Faker(Locale.forLanguageTag("es"));

    public static Alojamiento crearAlojamiento(){
        Alojamiento alojamiento = new Alojamiento();
        alojamiento.setNombre(faker.company().name());
        alojamiento.setDireccion(faker.address().streetAddress());
        alojamiento.setUbicacion(faker.address().country());
        alojamiento.setPrecio(faker.number().numberBetween(10,999));
        alojamiento.setTipo("Hotel");
        alojamiento.setHuespedes(faker.number().numberBetween(1,7));
        alojamiento.setHabitaciones(faker.number().numberBetween(10,100));
        alojamiento.setActivo(true);
        return alojamiento;
    }

    public static List<Alojamiento> crearAlojamiento(int numElementos){
        List<Alojamiento> alojamientos = new ArrayList<>();
        for(int i = 0; i <= numElementos; i ++){
            Alojamiento a = crearAlojamiento();
            alojamientos.add(a);
        }
        return alojamientos;
    }
}
