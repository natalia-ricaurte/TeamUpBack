package com.example.TeamUp.Controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UsuarioDetailDTO> getUsuarioPerfil(@RequestParam String email) {
    UsuarioEntity usuario = usuarioService.getUsuarioByEmail(email);
    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    UsuarioDetailDTO usuarioDTO = modelMapper.map(usuario, UsuarioDetailDTO.class);
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
}
