package dev.arielalvesdutra.ml.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ProdutoCaracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Valor da característica não pode ser vazio")
    private String valor;
    private String complemento;

    @ManyToOne
    @NotNull(message = "Produto é obrigatório!")
    private Produto produto;
    @ManyToOne
    @NotNull(message = "Característica é obrigatória!")
    private Caracteristica caracteristica;

    public ProdutoCaracteristica() {
    }

    public Long getId() {
        return id;
    }

    public ProdutoCaracteristica setId(Long id) {
        this.id = id;
        return this;
    }

    public Produto getProduto() {
        return produto;
    }

    public ProdutoCaracteristica setProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public ProdutoCaracteristica setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
        return this;
    }

    public String getValor() {
        return valor;
    }

    public ProdutoCaracteristica setValor(String valor) {
        this.valor = valor;
        return this;
    }

    public String getComplemento() {
        return complemento;
    }

    public ProdutoCaracteristica setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    @Override
    public String toString() {
        return "ProdutoCaracteristica{" +
                "id=" + id +
                ", valor='" + valor + '\'' +
                ", complemento='" + complemento + '\'' +
                '}';
    }
}
