package com.example.TeamUp.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TeamUp.Entities.MateriaEntity;
import com.example.TeamUp.Entities.UsuarioEntity;
import com.example.TeamUp.Repositories.MateriaRepository;
import com.example.TeamUp.Repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioMateriaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    /**
     * Asocia una materia existente a un usuario.
     */
    @Transactional
    public MateriaEntity addMateriaToUsuario(Long usuarioId, Long materiaId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociar la materia con id = {} al usuario con id = {}", materiaId, usuarioId);
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado"));
        MateriaEntity materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("La materia no fue encontrada"));

        if (!usuario.getMaterias().contains(materia)) {
            usuario.getMaterias().add(materia);
            materia.getUsuarios().add(usuario);
        }

        log.info("Finaliza proceso de asociar la materia con id = {} al usuario con id = {}", materiaId, usuarioId);
        return materia;
    }

    /**
     * Obtiene todas las materias asociadas a un usuario.
     */
    @Transactional
    public List<MateriaEntity> getMateriasByUsuario(Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar materias asociadas al usuario con id = {}", usuarioId);
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado"));

        log.info("Finaliza proceso de consultar materias asociadas al usuario con id = {}", usuarioId);
        return usuario.getMaterias();
    }

    /**
     * Reemplaza todas las materias asociadas a un usuario.
     */
    @Transactional
    public List<MateriaEntity> replaceMateriasInUsuario(Long usuarioId, List<MateriaEntity> materias)
            throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar materias asociadas al usuario con id = {}", usuarioId);
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado"));

        // Verificar que todas las materias existen
        for (MateriaEntity materia : materias) {
            if (!materiaRepository.existsById(materia.getId())) {
                throw new EntityNotFoundException("La materia con id = " + materia.getId() + " no fue encontrada");
            }
        }

        // Limpiar las relaciones previas
        usuario.getMaterias().forEach(m -> m.getUsuarios().remove(usuario));
        usuario.getMaterias().clear();

        // Añadir las nuevas relaciones
        for (MateriaEntity materia : materias) {
            materia.getUsuarios().add(usuario);
            usuario.getMaterias().add(materia);
        }

        log.info("Finaliza proceso de reemplazar materias asociadas al usuario con id = {}", usuarioId);
        return usuario.getMaterias();
    }

    /**
     * Elimina una materia de un usuario.
     */
    @Transactional
    public void removeMateriaFromUsuario(Long usuarioId, Long materiaId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminar la materia con id = {} del usuario con id = {}", materiaId, usuarioId);
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado"));
        MateriaEntity materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("La materia no fue encontrada"));

        if (!usuario.getMaterias().contains(materia)) {
            throw new EntityNotFoundException("La materia con id = " + materiaId + " no está asociada al usuario con id = " + usuarioId);
        }

        // Remover las relaciones
        usuario.getMaterias().remove(materia);
        materia.getUsuarios().remove(usuario);

        log.info("Finaliza proceso de eliminar la materia con id = {} del usuario con id = {}", materiaId, usuarioId);
    }
}
