package com.example.TeamUp.Services;

import com.example.TeamUp.Entities.UsuarioEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TeamUp.Repositories.UsuarioRepository;


@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //get todos los usuarios
    public List<UsuarioEntity> getUsuarios() {
        return usuarioRepository.findAll();
    }

    //get usuario por id
    public UsuarioEntity getUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalStateException("Usuario con id " + id + " no existe"));
    }

    //crear usuario
    public UsuarioEntity addUsuario(UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    //actualizar usuario
    public UsuarioEntity updateUsuario(Long id, UsuarioEntity usuario) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).orElseThrow(() -> new IllegalStateException("Usuario con id " + id + " no existe"));
        usuarioEntity.setNombre(usuario.getNombre());
        usuarioEntity.setCarrera(usuario.getCarrera());
        usuarioEntity.setLogin(usuario.getLogin());
        usuarioEntity.setPassword(usuario.getPassword());
        usuarioEntity.setReputacion(usuario.getReputacion());
        usuarioEntity.setCalificacion(usuario.getCalificacion());
        return usuarioRepository.save(usuarioEntity);
    }


    //borrar usuario
    public void deleteUsuario(Long id) {
        boolean exists = usuarioRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Usuario con id " + id + " no existe");
        }
        usuarioRepository.deleteById(id);
    }
    
}
