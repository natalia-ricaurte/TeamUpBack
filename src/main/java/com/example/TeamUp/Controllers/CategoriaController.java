package com.example.TeamUp.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeamUp.Entities.CategoriaEntity;
import com.example.TeamUp.Services.CategoriaService;
import com.example.TeamUp.dto.CategoriaDTO;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaEntity> getCategorias() {
        return categoriaService.getCategorias();
    }

    @GetMapping("/{id}")
    public Optional<CategoriaEntity> getCategoriaById(@PathVariable Long id) {
        return categoriaService.getCategoriaById(id);
    }

    @PostMapping
    public CategoriaEntity saveCategoria(CategoriaEntity categoria) {
        return categoriaService.saveCategoria(categoria);
    }

    @PutMapping("/{id}")
    public CategoriaEntity updateCategoria(@PathVariable Long id, @RequestBody CategoriaEntity categoria) {
        categoria.setId(id);
        return categoriaService.saveCategoria(categoria);
    }

    @PostMapping("/{id}/delete")
    public void deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteCategoria(id);
    }



    
}
