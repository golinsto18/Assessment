package model;

import lombok.Data;

@Data
public class Usuario {
    private Integer id;
    private String nome;
    private String senha;
}