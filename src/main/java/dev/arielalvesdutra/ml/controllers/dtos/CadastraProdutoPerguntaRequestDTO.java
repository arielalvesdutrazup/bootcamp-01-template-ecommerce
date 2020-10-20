package dev.arielalvesdutra.ml.controllers.dtos;

import javax.validation.constraints.NotBlank;

public class CadastraProdutoPerguntaRequestDTO {

    @NotBlank(message = "Título é obrigatório!")
    private String titulo;

    public CadastraProdutoPerguntaRequestDTO() {
    }

    public String getTitulo() {
        return titulo;
    }

    public CadastraProdutoPerguntaRequestDTO setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }
}
