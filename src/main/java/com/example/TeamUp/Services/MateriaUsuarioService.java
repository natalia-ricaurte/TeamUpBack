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
public class MateriaUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    /**
     * Asocia un usuario existente a una materia.
     *
     * @param materiaId Identificador de la materia.
     * @param usuarioId Identificador del usuario.
     * @return Instancia de UsuarioEntity asociada a la materia.
     * @throws EntityNotFoundException si la materia o el usuario no existen.
     */
    @Transactional
    public UsuarioEntity addUsuarioToMateria(Long materiaId, Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociar el usuario con id = {} a la materia con id = {}", usuarioId, materiaId);
        MateriaEntity materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("La materia no fue encontrada"));
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado"));

        if (!materia.getUsuarios().contains(usuario)) {
            materia.getUsuarios().add(usuario);
            usuario.getMaterias().add(materia);
        }

        log.info("Finaliza proceso de asociar el usuario con id = {} a la materia con id = {}", usuarioId, materiaId);
        return usuario;
    }

    /**
     * Obtiene todos los usuarios asociados a una materia.
     *
     * @param materiaId Identificador de la materia.
     * @return Lista de usuarios asociados a la materia.
     * @throws EntityNotFoundException si la materia no existe.
     */
    @Transactional
    public List<UsuarioEntity> getUsuariosByMateria(Long materiaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar usuarios asociados a la materia con id = {}", materiaId);
        MateriaEntity materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("La materia no fue encontrada"));

        log.info("Finaliza proceso de consultar usuarios asociados a la materia con id = {}", materiaId);
        return materia.getUsuarios();
    }

    /**
     * Reemplaza todos los usuarios asociados a una materia.
     *
     * @param materiaId Identificador de la materia.
     * @param usuarios  Lista de usuarios a asociar a la materia.
     * @return Lista actualizada de usuarios asociados.
     * @throws EntityNotFoundException si la materia o algún usuario no existen.
     */
    @Transactional
    public List<UsuarioEntity> replaceUsuariosInMateria(Long materiaId, List<UsuarioEntity> usuarios)
            throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar usuarios asociados a la materia con id = {}", materiaId);
        MateriaEntity materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("La materia no fue encontrada"));

        // Verificar que todos los usuarios existen
        for (UsuarioEntity usuario : usuarios) {
            if (!usuarioRepository.existsById(usuario.getId())) {
                throw new EntityNotFoundException("El usuario con id = " + usuario.getId() + " no fue encontrado");
            }
        }

        // Limpiar las relaciones previas
        materia.getUsuarios().forEach(u -> u.getMaterias().remove(materia));
        materia.getUsuarios().clear();

        // Añadir las nuevas relaciones
        for (UsuarioEntity usuario : usuarios) {
            usuario.getMaterias().add(materia);
            materia.getUsuarios().add(usuario);
        }

        log.info("Finaliza proceso de reemplazar usuarios asociados a la materia con id = {}", materiaId);
        return materia.getUsuarios();
    }

    /**
     * Elimina un usuario de una materia.
     *
     * @param materiaId Identificador de la materia.
     * @param usuarioId Identificador del usuario.
     * @throws EntityNotFoundException si la materia o el usuario no existen.
     */
    @Transactional
    public void removeUsuarioFromMateria(Long materiaId, Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminar el usuario con id = {} de la materia con id = {}", usuarioId, materiaId);
        MateriaEntity materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new EntityNotFoundException("La materia no fue encontrada"));
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no fue encontrado"));

        if (!materia.getUsuarios().contains(usuario)) {
            throw new EntityNotFoundException("El usuario con id = " + usuarioId + " no está asociado a la materia con id = " + materiaId);
        }

        // Remover las relaciones
        materia.getUsuarios().remove(usuario);
        usuario.getMaterias().remove(materia);

        log.info("Finaliza proceso de eliminar el usuario con id = {} de la materia con id = {}", usuarioId, materiaId);
    }
}
