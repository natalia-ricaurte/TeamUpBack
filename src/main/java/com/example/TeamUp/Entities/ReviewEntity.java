package com.example.TeamUp.Entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String texto;
    private Integer reputacion;
    private Integer calificacion;
    

    @ManyToOne
    //@JsonBackReference
    private UsuarioEntity usuarioEscritor;

    @ManyToOne
    //@JsonBackReference
    private UsuarioEntity usuarioRecibido;


    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<CategoriaEntity> categorias = new ArrayList<>();
}
