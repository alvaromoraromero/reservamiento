package com.example.reservamiento.repository;

import com.example.reservamiento.model.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlojamientosRepository extends JpaRepository<Alojamiento,Integer> {

    @Query(value = "select * from alojamientos where id_alojamiento=:id", nativeQuery = true)
    Alojamiento findTopByIdAlojamiento(@Param("id") String id_alojamiento);
    @Query(value = "select distinct(ubicacion) from alojamientos where activo=1 order by ubicacion;", nativeQuery = true)
    List<String> buscarDistinctUbicacion();
    @Query(value = "SELECT * FROM alojamientos a WHERE ubicacion=:destino and activo=1 and (ceil(:personas/a.huespedes))<=(SELECT (a.habitaciones)-ceil(COALESCE(sum(r.personas),0)/a.huespedes) from reservas r where ((:fechainicio between r.fecha_inicio and r.fecha_fin) or (:fechafin between r.fecha_inicio and r.fecha_fin) or ((r.fecha_inicio between :fechainicio and :fechafin) and (r.fecha_fin between :fechainicio and :fechafin))) and r.id_alojamiento = a.id_alojamiento);", nativeQuery = true)
    List<Alojamiento> buscarAlojamientos(@Param("destino") String destino, @Param("fechainicio") String fechainicio, @Param("fechafin") String fechafin, @Param("personas") int personas);
    @Query(value = "SELECT * FROM alojamientos WHERE nombre LIKE %:query% OR direccion LIKE %:query% OR ubicacion LIKE %:query%", nativeQuery = true)
    List<Alojamiento> buscarAlojamientos(@Param("query") String query);
    @Query(value = "update alojamientos set activo=0 where id_alojamiento=:id", nativeQuery = true)
    void disableById(@Param("id") String id_alojamiento);
}
