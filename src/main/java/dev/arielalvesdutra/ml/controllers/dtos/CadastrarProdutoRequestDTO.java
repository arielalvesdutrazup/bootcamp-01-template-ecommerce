package dev.arielalvesdutra.ml.controllers.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

public class CadastrarProdutoRequestDTO {

    @NotBlank(message = "{nome.naovazio}")
    private String nome;
    @Positive
    private BigDecimal valor;
    @NotNull(message = "{quantidade.obrigatoria}")
    @Positive
    private Integer quantidade;
    @NotBlank(message = "Descrição é obrigatória!")
    private String descricao;
    @NotNull(message = "Id da categoria é obrigatório!")
    private Long categoriaId;
    @Size(min = 3, message = "Mínimo de {min} características!")
    @NotNull(message = "Características são obrigatórias!")
    private Set<ProdutoCaracteristicaRequestDTO> caracteristicas;

    public CadastrarProdutoRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public CadastrarProdutoRequestDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public CadastrarProdutoRequestDTO setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public CadastrarProdutoRequestDTO setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public CadastrarProdutoRequestDTO setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public CadastrarProdutoRequestDTO setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
        return this;
    }

    public Set<ProdutoCaracteristicaRequestDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public CadastrarProdutoRequestDTO setCaracteristicas(Set<ProdutoCaracteristicaRequestDTO> caracteristicas) {
        this.caracteristicas = caracteristicas;
        return this;
    }
}
