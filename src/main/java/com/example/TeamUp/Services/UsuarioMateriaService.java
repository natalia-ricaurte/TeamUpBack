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
     *
     * @param usuarioId Identificador del usuario.
     * @param materiaId Identificador de la materia.
     * @return Instancia de MateriaEntity asociada al usuario.
     * @throws EntityNotFoundException si el usuario o la materia no existen.
     */
    @Transactional
    public MateriaEntity addMateriaToUsuario(Long usuarioId, Long materiaId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociar una materia con id = {} al usuario con id = {}", materiaId, usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        Optional<MateriaEntity> materiaEntity = materiaRepository.findById(materiaId);

        if (usuarioEntity.isEmpty()) {
            throw new EntityNotFoundException("El usuario no fue encontrado");
        }

        if (materiaEntity.isEmpty()) {
            throw new EntityNotFoundException("La materia no fue encontrada");
        }

        materiaEntity.get().setUsuario(usuarioEntity.get());
        usuarioEntity.get().getMaterias().add(materiaEntity.get());
        materiaRepository.save(materiaEntity.get());
        log.info("Finaliza proceso de asociar una materia con id = {} al usuario con id = {}", materiaId, usuarioId);
        return materiaEntity.get();
    }

    /**
     * Obtiene todas las materias asociadas a un usuario.
     *
     * @param usuarioId Identificador del usuario.
     * @return Lista de materias asociadas al usuario.
     * @throws EntityNotFoundException si el usuario no existe.
     */
    @Transactional
    public List<MateriaEntity> getMateriasByUsuario(Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar materias asociadas al usuario con id = {}", usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);

        if (usuarioEntity.isEmpty()) {
            throw new EntityNotFoundException("El usuario no fue encontrado");
        }

        log.info("Finaliza proceso de consultar materias asociadas al usuario con id = {}", usuarioId);
        return usuarioEntity.get().getMaterias();
    }

    /**
     * Reemplaza todas las materias asociadas a un usuario.
     *
     * @param usuarioId Identificador del usuario.
     * @param materias  Lista de materias a asociar al usuario.
     * @return Lista actualizada de materias asociadas.
     * @throws EntityNotFoundException si el usuario o alguna materia no existe.
     */
    @Transactional
    public List<MateriaEntity> replaceMateriasInUsuario(Long usuarioId, List<MateriaEntity> materias)
            throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar materias asociadas al usuario con id = {}", usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);

        if (usuarioEntity.isEmpty()) {
            throw new EntityNotFoundException("El usuario no fue encontrado");
        }

        for (MateriaEntity materia : materias) {
            Optional<MateriaEntity> materiaEntity = materiaRepository.findById(materia.getId());
            if (materiaEntity.isEmpty()) {
                throw new EntityNotFoundException("Una de las materias no fue encontrada");
            }
        }

        usuarioEntity.get().getMaterias().clear();
        usuarioEntity.get().getMaterias().addAll(materias);

        for (MateriaEntity materia : materias) {
            materia.setUsuario(usuarioEntity.get());
            materiaRepository.save(materia);
        }

        log.info("Finaliza proceso de reemplazar materias asociadas al usuario con id = {}", usuarioId);
        return usuarioEntity.get().getMaterias();
    }

    /**
     * Elimina una materia de un usuario.
     *
     * @param usuarioId Identificador del usuario.
     * @param materiaId Identificador de la materia.
     * @throws EntityNotFoundException si el usuario o la materia no existen.
     */
    @Transactional
    public void removeMateriaFromUsuario(Long usuarioId, Long materiaId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminar la materia con id = {} del usuario con id = {}", materiaId, usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        Optional<MateriaEntity> materiaEntity = materiaRepository.findById(materiaId);

        if (usuarioEntity.isEmpty()) {
            throw new EntityNotFoundException("El usuario no fue encontrado");
        }

        if (materiaEntity.isEmpty() || !materiaEntity.get().getUsuario().equals(usuarioEntity.get())) {
            throw new EntityNotFoundException("La materia no está asociada al usuario");
        }

        materiaEntity.get().setUsuario(null);
        usuarioEntity.get().getMaterias().remove(materiaEntity.get());
        materiaRepository.save(materiaEntity.get());

        log.info("Finaliza proceso de eliminar la materia con id = {} del usuario con id = {}", materiaId, usuarioId);
    }
}

