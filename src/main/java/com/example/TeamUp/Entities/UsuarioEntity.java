package com.example.TeamUp.Entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST)
    private List<MateriaEntity> materias = new ArrayList<>();
}
