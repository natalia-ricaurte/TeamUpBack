package com.example.TeamUp.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeamUp.Entities.MateriaEntity;
import com.example.TeamUp.Services.MateriaService;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    //Get all materias
    @GetMapping
    public List<MateriaEntity> getAllMaterias() {
        return materiaService.getMaterias();
    }

    //Get materia by id
    @GetMapping("/{id}")
    public Optional<MateriaEntity> getMateriaById(@PathVariable Long id) {
        return materiaService.getMateriaById(id);
    }

    //Create materia
    @PostMapping
    public MateriaEntity createMateria(MateriaEntity materia) {
        return materiaService.saveMateria(materia);
    }

    @PutMapping("/{id}")
    public MateriaEntity updateMateria(@PathVariable Long id, @RequestBody MateriaEntity materia) {
        materia.setId(id);
        return materiaService.saveMateria(materia);
    }

    //delete materia
    @DeleteMapping("/{id}")
    public void deleteMateria(@PathVariable Long id) {
        materiaService.deleteMateria(id);
    }
    


    
    
}
