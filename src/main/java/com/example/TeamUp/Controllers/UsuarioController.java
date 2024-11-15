package com.example.TeamUp.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeamUp.Entities.UsuarioEntity;
import com.example.TeamUp.Services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //get todos los usuarios
    @GetMapping
    public List<UsuarioEntity> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    //get usuario por id
    @GetMapping("/{id}")
    public UsuarioEntity getUsuario(@PathVariable Long id) {
        return usuarioService.getUsuario(id);
    }

    //actualizar un usuario
    @PutMapping("/{id}")
    public UsuarioEntity updateUsuario(@PathVariable Long id, @RequestBody UsuarioEntity usuario) {
        return usuarioService.updateUsuario(id, usuario);
    }

    //añadir un usuario
    @PutMapping
    public UsuarioEntity addUsuario(@RequestBody UsuarioEntity usuario) {
        return usuarioService.addUsuario(usuario);
    }

    //eliminar un usuario
    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }
    

}
