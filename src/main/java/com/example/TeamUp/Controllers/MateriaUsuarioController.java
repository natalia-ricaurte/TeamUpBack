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

import com.example.TeamUp.Entities.UsuarioEntity;
import com.example.TeamUp.Services.MateriaUsuarioService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/materias")
public class MateriaUsuarioController {

    @Autowired
    private MateriaUsuarioService materiaUsuarioService;

    /**
     * Asocia un usuario existente a una materia existente.
     *
     * @param materiaId El ID de la materia que se va a asociar
     * @param usuarioId El ID del usuario que se va a asociar a la materia
     * @return El usuario asociado.
     */
    @PostMapping(value = "/{materiaId}/usuarios/{usuarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public UsuarioEntity addUsuarioToMateria(@PathVariable("materiaId") Long materiaId, @PathVariable("usuarioId") Long usuarioId)
            throws EntityNotFoundException {
        return materiaUsuarioService.addUsuarioToMateria(materiaId, usuarioId);
    }

    /**
     * Busca y devuelve todos los usuarios asociados a una materia.
     *
     * @param materiaId El ID de la materia
     * @return Lista de usuarios asociados a la materia.
     */
    @GetMapping(value = "/{materiaId}/usuarios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UsuarioEntity> getUsuariosFromMateria(@PathVariable("materiaId") Long materiaId) throws EntityNotFoundException {
        return materiaUsuarioService.getUsuariosByMateria(materiaId);
    }

    /**
     * Reemplaza la lista de usuarios asociados a una materia.
     *
     * @param materiaId El ID de la materia
     * @param usuarios  Lista de usuarios a asociar a la materia
     * @return Lista de usuarios actualizada.
     */
    @PutMapping(value = "/{materiaId}/usuarios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UsuarioEntity> replaceUsuariosInMateria(@PathVariable("materiaId") Long materiaId, @RequestBody List<UsuarioEntity> usuarios)
            throws EntityNotFoundException {
        return materiaUsuarioService.replaceUsuariosInMateria(materiaId, usuarios);
    }

    /**
     * Elimina la asociación entre un usuario y una materia.
     *
     * @param materiaId El ID de la materia
     * @param usuarioId El ID del usuario que se desasocia
     */
    @DeleteMapping(value = "/{materiaId}/usuarios/{usuarioId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeUsuarioFromMateria(@PathVariable("materiaId") Long materiaId, @PathVariable("usuarioId") Long usuarioId)
            throws EntityNotFoundException {
        materiaUsuarioService.removeUsuarioFromMateria(materiaId, usuarioId);
    }
}
