package com.example.TeamUp.Entities;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String carrera;
    private String login;
    private String password;
    private Integer reputacion;
    private Integer calificacion;

    @OneToMany(mappedBy = "usuarioEscritor", cascade = CascadeType.PERSIST)
    private List<ReviewEntity> reviewsEscritas = new ArrayList<>();

    @OneToMany(mappedBy = "usuarioRecibido", cascade = CascadeType.PERSIST)
    private List<ReviewEntity> reviewsRecibidas = new ArrayList<>();

    @ManyToMany(mappedBy = "usuarios", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<MateriaEntity> materias = new ArrayList<>();
}
