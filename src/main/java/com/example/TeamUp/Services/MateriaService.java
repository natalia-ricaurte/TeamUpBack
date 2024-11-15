package com.example.TeamUp.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TeamUp.Entities.MateriaEntity;
import com.example.TeamUp.Repositories.MateriaRepository;

@Service
public class MateriaService {
    
    @Autowired
    private MateriaRepository materiaRepository;

    
    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    //get todas las materias
    public List<MateriaEntity> getMaterias() {
        return materiaRepository.findAll();
    }

    //get materia por id
    public Optional<MateriaEntity> getMateriaById(Long id) {
        return materiaRepository.findById(id);
    }

    //Crear o actualziar materia
    public MateriaEntity saveMateria(MateriaEntity materia) {
        return materiaRepository.save(materia);
    }

    //Borrar materia
    public void deleteMateria(Long id) {
        materiaRepository.deleteById(id);
    }

    
}
