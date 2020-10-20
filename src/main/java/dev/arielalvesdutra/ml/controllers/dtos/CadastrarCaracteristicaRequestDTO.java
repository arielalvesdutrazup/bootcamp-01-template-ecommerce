package dev.arielalvesdutra.ml.controllers.dtos;

import javax.validation.constraints.NotBlank;

public class CadastrarCaracteristicaRequestDTO {

    @NotBlank(message = "{nome.naovazio}")
    private String nome;

    public CadastrarCaracteristicaRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public CadastrarCaracteristicaRequestDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }
}
