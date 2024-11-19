package com.example.TeamUp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
import com.example.TeamUp.dto.MateriaDTO;
import com.example.TeamUp.dto.MateriaDetailDTO;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioMateriaController {

    @Autowired
    private UsuarioMateriaService usuarioMateriaService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Asocia una materia existente a un usuario.
     *
     * @param usuarioId El ID del usuario
     * @param materiaId El ID de la materia
     * @return La materia asociada al usuario.
     */
    @PostMapping(value = "/{usuarioId}/materias/{materiaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public MateriaDetailDTO addMateriaToUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("materiaId") Long materiaId)
            throws EntityNotFoundException {
        MateriaEntity materiaEntity = usuarioMateriaService.addMateriaToUsuario(usuarioId, materiaId);
        return modelMapper.map(materiaEntity, MateriaDetailDTO.class);
    }

    /**
     * Obtiene todas las materias asociadas a un usuario.
     *
     * @param usuarioId El ID del usuario
     * @return Lista de materias asociadas al usuario.
     */
    @GetMapping(value = "/{usuarioId}/materias")
    @ResponseStatus(code = HttpStatus.OK)
    public List<MateriaDetailDTO> getMateriasFromUsuario(@PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        List<MateriaEntity> materiaEntities = usuarioMateriaService.getMateriasByUsuario(usuarioId);
        return modelMapper.map(materiaEntities, new TypeToken<List<MateriaDetailDTO>>() {}.getType());
    }

    /**
     * Reemplaza la lista de materias asociadas a un usuario.
     *
     * @param usuarioId El ID del usuario
     * @param materias  Lista de materias a asociar al usuario
     * @return Lista actualizada de materias asociadas al usuario.
     */
    @PutMapping(value = "/{usuarioId}/materias")
    @ResponseStatus(code = HttpStatus.OK)
    public List<MateriaDetailDTO> replaceMateriasInUsuario(@PathVariable("usuarioId") Long usuarioId, @RequestBody List<MateriaDTO> materias)
            throws EntityNotFoundException {
        List<MateriaEntity> entities = modelMapper.map(materias, new TypeToken<List<MateriaEntity>>() {}.getType());
        List<MateriaEntity> materiaList = usuarioMateriaService.replaceMateriasInUsuario(usuarioId, entities);
        return modelMapper.map(materiaList, new TypeToken<List<MateriaDetailDTO>>() {}.getType());
    }

    /**
     * Elimina la asociación entre una materia y un usuario.
     *
     * @param usuarioId El ID del usuario
     * @param materiaId El ID de la materia que se desasocia
     */
    @DeleteMapping(value = "/{usuarioId}/materias/{materiaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeMateria(@PathVariable("usuarioId") Long usuarioId, @PathVariable("materiaId") Long materiaId)
            throws EntityNotFoundException {
        usuarioMateriaService.removeMateriaFromUsuario(usuarioId, materiaId);
    }
}

