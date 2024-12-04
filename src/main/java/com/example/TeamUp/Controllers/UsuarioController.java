package com.example.TeamUp.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeamUp.Entities.UsuarioEntity;
import com.example.TeamUp.Services.UsuarioService;
import com.example.TeamUp.dto.MateriaDTO;
import com.example.TeamUp.dto.UsuarioDTO;
import com.example.TeamUp.dto.UsuarioDetailDTO;
import com.example.TeamUp.exceptions.EntityNotFoundException;
import com.example.TeamUp.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> getUsuarioPerfil(@RequestParam String email) throws EntityNotFoundException {
        UsuarioEntity usuario = usuarioService.getUsuarioConMaterias(email);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

      
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);

       
        List<MateriaDTO> materiasDTO = usuario.getMaterias().stream()
            .map(materia -> {
                MateriaDTO materiaDTO = new MateriaDTO();
                materiaDTO.setId(materia.getId());
                materiaDTO.setNombre(materia.getNombre());
                return materiaDTO;
            })
            .collect(Collectors.toList());
        usuarioDTO.setMateriasIds(materiasDTO);

        return ResponseEntity.ok(usuarioDTO);
    }




    // Obtener todos los usuarios
    @GetMapping
    public List<UsuarioDetailDTO> getUsuarios() throws IllegalOperationException {
        List<UsuarioEntity> usuarios = usuarioService.getUsuarios();
        return modelMapper.map(usuarios, new TypeToken<List<UsuarioDetailDTO>>() {}.getType());
    }

    // Obtener usuario por id
    @GetMapping("/{id}")
    public UsuarioDetailDTO getUsuario(@PathVariable Long id) throws EntityNotFoundException {
        UsuarioEntity usuario = usuarioService.getUsuario(id);
        return modelMapper.map(usuario, new TypeToken<UsuarioDetailDTO>() {}.getType());
    }

    // Crear un usuario
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UsuarioDTO create(@RequestBody UsuarioDTO usuarioDTO) throws IllegalOperationException, EntityNotFoundException {
        UsuarioEntity usuarioEntity = usuarioService.addUsuario(modelMapper.map(usuarioDTO, UsuarioEntity.class));
        return modelMapper.map(usuarioEntity, UsuarioDTO.class);
    }

    // Eliminar un usuario
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        usuarioService.deleteUsuario(id);
    }

    @PostMapping("/auth")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> authenticateUser(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            System.out.println("Datos recibidos: " + usuarioDTO); // Agregar este log
            UsuarioEntity usuario = usuarioService.authenticateUsuario(usuarioDTO.getAction(), usuarioDTO);
            return ResponseEntity.ok(modelMapper.map(usuario, UsuarioDTO.class));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor.");
        }
    }





}
