package com.example.TeamUp.Entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String texto;
    private Integer reputacion;
    private Integer calificacion;

    @ManyToOne
    private UsuarioEntity usuarioEscritor;

    @ManyToOne
    private UsuarioEntity usuarioRecibido;

    @ManyToOne
    private MateriaEntity materia;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<CategoriaEntity> categorias = new ArrayList<>();
}
