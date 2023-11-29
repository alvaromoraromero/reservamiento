package com.example.reservamiento.service;


import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.repository.AlojamientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlojamientosService {

    @Autowired
    private AlojamientosRepository alojamientosRepository;

    public Alojamiento buscarPorId(String id) {
        return alojamientosRepository.findTopByIdAlojamiento(id);
    }

    public List<Alojamiento> buscarAlojamientos(String destino, String fechainicio, String fechafin, int personas) {
        return alojamientosRepository.buscarAlojamientos(destino, fechainicio, fechafin, personas);
    }
    public List<Alojamiento> buscarAlojamientos(String query) {
        return alojamientosRepository.buscarAlojamientos(query);
    }
    public List<String> desplegableUbicaciones() {
        return alojamientosRepository.buscarDistinctUbicacion();
    }
    public List<Alojamiento> obtenerAlojamientos() {
        return alojamientosRepository.findAll();
    }

    public Alojamiento guardarAlojamiento (Alojamiento alojamiento) {
        return alojamientosRepository.save(alojamiento);
    }
    public void deshabilitarPorId(String id_alojamiento) {
        alojamientosRepository.disableById(id_alojamiento);
    }
}
