package com.example.reservamiento.component;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ThymeMath {
    public int ceil(double num1, double num2) {
        return (int)Math.ceil(num1/num2);
    }

    public String formatearFecha(LocalDateTime fecha) {
        return (fecha!=null) ? fecha.getDayOfMonth() + "/" + fecha.getMonthValue() + "/" + fecha.getYear() + " " + fecha.getHour() + ":" + fecha.getMinute() : "";
    }
}
