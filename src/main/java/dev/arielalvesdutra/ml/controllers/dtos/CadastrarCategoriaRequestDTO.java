package dev.arielalvesdutra.ml.controllers.dtos;

import javax.validation.constraints.NotBlank;

public class CadastrarCategoriaRequestDTO {

    @NotBlank(message = "Nome não pode ser vazio! ")
    private String nome;
    private Long categoriaMaeId;

    public CadastrarCategoriaRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public CadastrarCategoriaRequestDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Long getCategoriaMaeId() {
        return categoriaMaeId;
    }

    public CadastrarCategoriaRequestDTO setCategoriaMaeId(Long categoriaMaeId) {
        this.categoriaMaeId = categoriaMaeId;
        return this;
    }
}
