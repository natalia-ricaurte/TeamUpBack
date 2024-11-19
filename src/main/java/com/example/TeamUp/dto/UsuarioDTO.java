package com.example.TeamUp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String carrera;
    private String login;
    private String password;
    private Integer reputacion;
    private Integer calificacion;

}
