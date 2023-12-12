package dto;

import lombok.Data;

@Data
public class UsuarioDTOInput {
    private Integer id;
    private String nome;
    private String senha;
}