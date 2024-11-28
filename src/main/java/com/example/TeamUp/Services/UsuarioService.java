package com.example.TeamUp.Services;

import com.example.TeamUp.Entities.MateriaEntity;
import com.example.TeamUp.Entities.UsuarioEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TeamUp.Repositories.MateriaRepository;
import com.example.TeamUp.Repositories.UsuarioRepository;
import com.example.TeamUp.dto.UsuarioDTO;
import com.example.TeamUp.exceptions.EntityNotFoundException;


@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity getUsuarioByEmail(String email) {

        // Utilizamos el método del repositorio para buscar por lemail
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con el email: " + email);
        }
        return usuario;
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

    @Autowired
    private UsuarioMateriaService usuarioMateriaService;

    public UsuarioEntity registrarUsuario(UsuarioEntity usuario, List<Long> materiasIds) throws EntityNotFoundException {
        return usuarioMateriaService.registrarUsuarioConMaterias(usuario, materiasIds);
    }

    public UsuarioEntity validateLogin(String email, String password) throws EntityNotFoundException {

        UsuarioEntity usuario = usuarioRepository.findByEmail(email);
    
        if (usuario == null) {
            throw new EntityNotFoundException("Usuario no encontrado con el email: " + email);
        }
    

        if (!usuario.getPassword().equals(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }
    
        return usuario; 
    }

    public UsuarioEntity authenticateUsuario(String action, UsuarioDTO usuarioDTO) throws EntityNotFoundException {
        if ("register".equalsIgnoreCase(action)) {
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setEdad(usuarioDTO.getEdad());
            usuario.setPassword(usuarioDTO.getPassword());
            usuario.setCarrera(usuarioDTO.getCarrera());
            usuario.setHabilidades(usuarioDTO.getHabilidades());
        
            if (usuarioDTO.getMateriasIds() != null) {
                System.out.println("Materias recibidas: " + usuarioDTO.getMateriasIds());
                List<MateriaEntity> materias = usuarioDTO.getMateriasIds().stream()
                    .map(materiaDTO -> {
                        MateriaEntity materia = materiaRepository.findByNombre(materiaDTO.getNombre())
                            .orElseGet(() -> { 
                                MateriaEntity nuevaMateria = new MateriaEntity();
                                nuevaMateria.setNombre(materiaDTO.getNombre());
                                return nuevaMateria;
                            });
                        materia.getUsuarios().add(usuario); 
                        return materia;
                    })
                    .collect(Collectors.toList());
                usuario.setMaterias(materias); 
            }
            
            
            
            return usuarioRepository.save(usuario);
        } else if ("login".equalsIgnoreCase(action)) {
            return validateLogin(usuarioDTO.getEmail(), usuarioDTO.getPassword());
        } else {
            throw new IllegalArgumentException("Acción no válida");
        }
    }

    public UsuarioEntity getUsuarioConMaterias(String email) throws EntityNotFoundException {
   
    UsuarioEntity usuario = usuarioRepository.findByEmail(email);
    if (usuario == null) {
        throw new EntityNotFoundException("Usuario no encontrado con el email: " + email);
    }


    List<MateriaEntity> materias = usuarioMateriaService.getMateriasByUsuario(usuario.getId());
    usuario.setMaterias(materias);

    return usuario;
}



    
}
