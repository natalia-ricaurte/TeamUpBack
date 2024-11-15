package com.example.TeamUp.Entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;
    private Integer reputacion;
    private Integer calificacion;

    @ManyToOne
    private UsuarioEntity usuarioEscritor;

    @ManyToOne
    private UsuarioEntity usuarioRecibido;



    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
    private List<CategoriaEntity> categorias = new ArrayList<>();
}
