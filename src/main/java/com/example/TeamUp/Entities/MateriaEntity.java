package com.example.TeamUp.Entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class MateriaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    
    @ManyToMany
    @JsonBackReference
    private List<UsuarioEntity> usuarios = new ArrayList<>();

}
