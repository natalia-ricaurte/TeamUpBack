package com.example.TeamUp.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MateriaDetailDTO extends MateriaDTO{

    private List<UsuarioDTO> usuarios = new ArrayList<>();

}
