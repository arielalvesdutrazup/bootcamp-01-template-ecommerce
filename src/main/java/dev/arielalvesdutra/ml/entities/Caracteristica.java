package dev.arielalvesdutra.ml.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;


// 1 ProdutoCaracteristica.java

/**
 * Característica do produto.
 */
@Entity
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{nome.naovazio}")
    private String nome;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();
    @OneToMany(mappedBy = "caracteristica", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProdutoCaracteristica> produtos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Caracteristica setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Caracteristica setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public Caracteristica setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public Set<ProdutoCaracteristica> getProdutos() {
        return produtos;
    }

    public Caracteristica setProdutos(Set<ProdutoCaracteristica> produtos) {
        this.produtos = produtos;
        return this;
    }
}
