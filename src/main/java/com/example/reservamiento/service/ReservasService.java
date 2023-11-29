package com.example.reservamiento.service;


import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Reserva;
import com.example.reservamiento.repository.AlojamientosRepository;
import com.example.reservamiento.repository.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReservasService {

    @Autowired
    private ReservasRepository reservasRepository;
    @Autowired
    private AlojamientosRepository alojamientosRepository;

    public List<Reserva> obtenerReservas() {
        return reservasRepository.findAll();
    }
    public Reserva obtenerPorId(String id_reserva) {
        return reservasRepository.findTopByIdReserva(id_reserva);
    }
    public List<Reserva> buscarReservas(String query) {
        return reservasRepository.buscarReservas(query);
    }
    public void cancelarPorId(String id_reserva) {
        reservasRepository.cancelByIdReserva(id_reserva);
    }

    public Reserva guardarReserva(Reserva reserva) {
        return reservasRepository.save(reserva);
    }

    public List<Alojamiento> obtenerReservasTop() {
        List<String> listAlojamientosId = reservasRepository.obtenerReservasTop();
        List<Alojamiento> listAlojamientos = new ArrayList<>();
        for (String s : listAlojamientosId) {
            listAlojamientos.add(alojamientosRepository.findTopByIdAlojamiento(s));
        }
        return listAlojamientos;
    }

    public List<Reserva> obtenerReservasActivasUsuario(int idusuario) {
        return reservasRepository.obtenerReservasActivasUsuario(idusuario);
    }

    public List<Reserva> obtenerReservasPasadasUsuario(int idusuario) {
        return reservasRepository.obtenerReservasPasadasUsuario(idusuario);
    }

    public void eliminarPorId (Integer id) {
        reservasRepository.deleteById(id);
    }
}

