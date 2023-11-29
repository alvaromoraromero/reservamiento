package com.example.reservamiento.service;

import com.example.reservamiento.model.Usuario;
import com.example.reservamiento.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuariosService implements UserDetailsService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    public boolean existeUsuario(Usuario usuario){
        return usuariosRepository.existsByUsername(usuario.getUsername());
    }
    public boolean existeCorreo(Usuario usuario){
        return usuariosRepository.existsByEmail(usuario.getEmail());
    }

    public Usuario guardarUsuario(Usuario usuario){
        return usuariosRepository.save(usuario);
    }

    public List<Usuario> obtenerUsuarios(){
        return usuariosRepository.findAll();
    }
    public List<Usuario> buscarUsuarios(String query){
        return usuariosRepository.buscarUsuarios(query);
    }

    public Usuario actualizarUsuario(Usuario nuevo, String username) {
        Usuario old = usuariosRepository.findTopByUsername(username);
        old.setNombre(nuevo.getNombre());
        old.setApellidos(nuevo.getApellidos());
        old.setEmail(nuevo.getEmail());
        old.setUsername(nuevo.getUsername());
        old.setPassword(nuevo.getPassword());
        old.setTelefono(nuevo.getTelefono().length()>0 ? nuevo.getTelefono() : null);
        old.setGenero(nuevo.getGenero());
        return usuariosRepository.save(old);
    }

    public Usuario buscarPorId(String id_usuario) {
        return usuariosRepository.findTopByIdUsuario(id_usuario);
    }

    public Usuario desactivarUsuario(String id_usuario) {
        Usuario nuevo = usuariosRepository.findTopByIdUsuario(id_usuario);
        nuevo.setActivo(false);
        return usuariosRepository.save(nuevo);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Recuperamos el usuario
        Usuario usuario = usuariosRepository.findTopByUsername(username);

        if (usuario==null)
            usuario = usuariosRepository.findTopByEmail(username);

        if (usuario==null)
            throw new UsernameNotFoundException("No existe usuario");

        if (!usuario.isActivo())
            throw new UsernameNotFoundException("Usuario eliminado");

        //Mapeamos los roles
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(usuario.getRol().toString()));

        //Creamos y devolvemos un UserDetails con los datos del usuario
        return new User(usuario.getUsername(), usuario.getPassword(),roles);
    }
}
