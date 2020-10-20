package dev.arielalvesdutra.ml.controllers.dtos;

import javax.validation.constraints.*;

public class CadastrarProdutoOpiniaoRequestDTO {

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota mínima é de {value}!")
    @Max(value = 5, message = "Nota máxima é de {value}!")
    private Integer nota;
    @NotBlank(message = "Título é obrigatório!")
    private String titulo;
    @Size(max = 500, message = "Descrição deve ter até {max} caracteress!")
    @NotBlank(message = "Descrição é obrigatória!")
    private String descricao;

    public CadastrarProdutoOpiniaoRequestDTO() {
    }

    public Integer getNota() {
        return nota;
    }

    public CadastrarProdutoOpiniaoRequestDTO setNota(Integer nota) {
        this.nota = nota;
        return this;
    }

    public String getTitulo() {
        return titulo;
    }

    public CadastrarProdutoOpiniaoRequestDTO setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public CadastrarProdutoOpiniaoRequestDTO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }
}
