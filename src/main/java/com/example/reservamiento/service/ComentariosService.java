package com.example.reservamiento.service;


import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Comentario;
import com.example.reservamiento.model.Usuario;
import com.example.reservamiento.repository.ComentariosRepository;
import com.example.reservamiento.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComentariosService {

    @Autowired
    private ComentariosRepository comentariosRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<Comentario> obtenerComentarios() {
        return comentariosRepository.findAll();
    }
    public Comentario obtenerPorId(String id_comentario) {
        return comentariosRepository.findTopByIdComentario(id_comentario);
    }

    public List<Comentario> obtenerComentarioUsuario(Usuario usuario, String id_alojamiento) {
        return (usuario!=null) ? comentariosRepository.findTopByUsuarioAndAlojamiento(usuario.getId_usuario(), id_alojamiento) : null;
    }
    public List<Comentario> buscarComentarios(String query) {
        return comentariosRepository.buscarComentarios(query);
    }
    public void ocultarPorId(String id_comentario) {
        comentariosRepository.ocultarByIdComentario(id_comentario);
    }

    public Comentario guardarComentario(Comentario comentario) {
        return comentariosRepository.save(comentario);
    }

    public List<Comentario> obtenerComentariosActivosAlojamiento(String id_alojamiento, int id_usuario) {
        return comentariosRepository.obtenerComentariosActivosAlojamiento(id_alojamiento, id_usuario);
    }

    public void eliminarPorId (Integer id) {
        comentariosRepository.deleteById(id);
    }
}

