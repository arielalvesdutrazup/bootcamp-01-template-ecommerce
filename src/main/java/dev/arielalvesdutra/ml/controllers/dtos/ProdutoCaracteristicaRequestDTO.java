package dev.arielalvesdutra.ml.controllers.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProdutoCaracteristicaRequestDTO {

    @NotNull(message = "Id da caracteristica é obrigatório!")
    private Long caracteristicaId;
    @NotBlank(message = "Valor da característica é obrigatório!")
    private String valor;
    private String complemento;

    public ProdutoCaracteristicaRequestDTO() {
    }

    public Long getCaracteristicaId() {
        return caracteristicaId;
    }

    public ProdutoCaracteristicaRequestDTO setCaracteristicaId(Long caracteristicaId) {
        this.caracteristicaId = caracteristicaId;
        return this;
    }

    public String getValor() {
        return valor;
    }

    public ProdutoCaracteristicaRequestDTO setValor(String valor) {
        this.valor = valor;
        return this;
    }

    public String getComplemento() {
        return complemento;
    }

    public ProdutoCaracteristicaRequestDTO setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }
}
