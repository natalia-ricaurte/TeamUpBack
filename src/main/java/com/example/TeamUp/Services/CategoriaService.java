package com.example.TeamUp.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TeamUp.Entities.CategoriaEntity;
import com.example.TeamUp.Repositories.CategoriaRepository;

@Service
public class CategoriaService {
   
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //get todas las categorias
    public List<CategoriaEntity> getCategorias() {
        return categoriaRepository.findAll();
    }

    //get categoria por id
    public Optional<CategoriaEntity> getCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    //Crear o actualziar categoria
    public CategoriaEntity saveCategoria(CategoriaEntity categoria) {
        return categoriaRepository.save(categoria);
    }

    //Borrar categoria
    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
    
}
