package com.example.reservamiento.repository;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Comentario;
import com.example.reservamiento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentario, Integer> {

    @Query(value = "select * from comentarios where id_comentario=:id", nativeQuery = true)
    Comentario findTopByIdComentario(@Param("id") String id_comentario);
    @Query(value = "SELECT * FROM comentarios WHERE titulo LIKE %:query% OR descripcion LIKE %:query% OR id_alojamiento IN (SELECT id_alojamiento FROM alojamientos WHERE nombre LIKE %:query% AND activo=1)", nativeQuery = true)
    List<Comentario> buscarComentarios(@Param("query") String query);
    @Query(value = "update comentarios set mostrar=0 where id_comentario=:id", nativeQuery = true)
    void ocultarByIdComentario(@Param("id") String id_comentario);
    @Query(value = "select * from comentarios where id_alojamiento=:id_alojamiento AND id_usuario!=:id_usuario AND mostrar=1", nativeQuery = true)
    List<Comentario> obtenerComentariosActivosAlojamiento(String id_alojamiento, int id_usuario);

    @Query(value = "select * from comentarios where id_alojamiento=:id_alojamiento AND id_usuario=:id_usuario AND mostrar=1", nativeQuery = true)
    List<Comentario> findTopByUsuarioAndAlojamiento(int id_usuario, String id_alojamiento);
}
