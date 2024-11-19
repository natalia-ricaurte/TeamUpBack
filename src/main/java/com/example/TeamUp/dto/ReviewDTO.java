package com.example.TeamUp.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReviewDTO {

    private Long id;
    private String texto;
    private Integer reputacion;
    private Integer calificacion;
    private String habilidades;
    private String email;
    private UsuarioDTO usuarioEscritor;
    private UsuarioDTO usuarioRecibido;
   
    

}
