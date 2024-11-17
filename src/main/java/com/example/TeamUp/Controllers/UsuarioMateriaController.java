package com.example.TeamUp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.TeamUp.Entities.MateriaEntity;
import com.example.TeamUp.Services.UsuarioMateriaService;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/usuarios")
public class UsuarioMateriaController {

    @Autowired
    private UsuarioMateriaService usuarioMateriaService;

    /**
     * Asocia una materia existente a un usuario existente.
     *
     * @param usuarioId El ID del usuario
     * @param materiaId El ID de la materia a asociar
     * @return La materia asociada.
     */
    @PostMapping(value = "/{usuarioId}/materias/{materiaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public MateriaEntity addMateriaToUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("materiaId") Long materiaId)
            throws EntityNotFoundException {
        return usuarioMateriaService.addMateriaToUsuario(usuarioId, materiaId);
    }

    /**
     * Obtiene todas las materias asociadas a un usuario.
     *
     * @param usuarioId El ID del usuario
     * @return Lista de materias asociadas al usuario.
     */
    @GetMapping(value = "/{usuarioId}/materias")
    @ResponseStatus(code = HttpStatus.OK)
    public List<MateriaEntity> getMateriasFromUsuario(@PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        return usuarioMateriaService.getMateriasByUsuario(usuarioId);
    }

    /**
     * Reemplaza la lista de materias asociadas a un usuario.
     *
     * @param usuarioId El ID del usuario
     * @param materias  Lista de materias a asociar al usuario
     * @return Lista actualizada de materias asociadas.
     */
    @PutMapping(value = "/{usuarioId}/materias")
    @ResponseStatus(code = HttpStatus.OK)
    public List<MateriaEntity> replaceMateriasInUsuario(@PathVariable("usuarioId") Long usuarioId, @RequestBody List<MateriaEntity> materias)
            throws EntityNotFoundException {
        return usuarioMateriaService.replaceMateriasInUsuario(usuarioId, materias);
    }

    /**
     * Elimina la asociación entre una materia y un usuario.
     *
     * @param usuarioId El ID del usuario
     * @param materiaId El ID de la materia a desasociar
     */
    @DeleteMapping(value = "/{usuarioId}/materias/{materiaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeMateriaFromUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("materiaId") Long materiaId)
            throws EntityNotFoundException {
        usuarioMateriaService.removeMateriaFromUsuario(usuarioId, materiaId);
    }
}

