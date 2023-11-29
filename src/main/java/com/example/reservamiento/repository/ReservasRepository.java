package com.example.reservamiento.repository;

import com.example.reservamiento.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservasRepository extends JpaRepository<Reserva, Integer> {

    @Query(value = "select * from reservas where id_reserva=:id", nativeQuery = true)
    Reserva findTopByIdReserva(@Param("id") String id_reserva);
    @Query(value = "SELECT * FROM reservas WHERE id_usuario IN (SELECT id_usuario FROM usuarios WHERE username LIKE %:query%) OR id_alojamiento IN (SELECT id_alojamiento FROM alojamientos WHERE nombre LIKE %:query%)", nativeQuery = true)
    List<Reserva> buscarReservas(@Param("query") String query);
    @Query(value = "update reservas set activo=0 where id_reserva=:id", nativeQuery = true)
    void cancelByIdReserva(@Param("id") String id_reserva);
    @Query(value = "SELECT a.id_alojamiento FROM alojamientos a, reservas r WHERE a.id_alojamiento = r.id_alojamiento AND a.activo=1 AND r.activo=1 GROUP BY r.id_alojamiento ORDER BY COUNT(r.id_alojamiento) DESC LIMIT 4;", nativeQuery = true)
    List<String> obtenerReservasTop();
    @Query(value = "select * from reservas where fecha_fin>current_date and id_usuario=:idusuario", nativeQuery = true)
    List<Reserva> obtenerReservasActivasUsuario(@Param("idusuario") int idusuario);
    @Query(value = "select * from reservas where fecha_fin<=current_date and id_usuario=:idusuario", nativeQuery = true)
    List<Reserva> obtenerReservasPasadasUsuario(@Param("idusuario") int idusuario);

}
