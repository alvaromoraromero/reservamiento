package com.example.reservamiento.repository;

import java.util.List;
import java.util.Optional;

import com.example.reservamiento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    @Query(value = "select * from usuarios where id_usuario=:id", nativeQuery = true)
    Usuario findTopByIdUsuario(@Param("id") String id_usuario);
    @Query(value = "SELECT * FROM usuarios WHERE username LIKE %:query% OR email LIKE %:query% OR nombre LIKE %:query% OR apellidos LIKE %:query%", nativeQuery = true)
    List<Usuario> buscarUsuarios(@Param("query") String query);
    Usuario findTopByUsername(String username);
    Usuario findTopByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}