package com.example.TeamUp.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String descripcion;
    private Float calificacion;

    @ManyToOne
    @JsonBackReference
    private ReviewEntity review;
}
