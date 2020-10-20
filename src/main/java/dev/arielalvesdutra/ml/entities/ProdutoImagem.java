package dev.arielalvesdutra.ml.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class ProdutoImagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "URL da foto é obrigatória!")
    private String url;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();

    @NotNull(message = "Produto é obrigatório!")
    @ManyToOne
    private Produto produto;

    public ProdutoImagem() {
    }

    public Long getId() {
        return id;
    }

    public ProdutoImagem setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ProdutoImagem setUrl(String url) {
        this.url = url;
        return this;
    }

    public Produto getProduto() {
        return produto;
    }

    public ProdutoImagem setProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public ProdutoImagem setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    @Override
    public String toString() {
        return "ProdutoFoto{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", cadastradoEm=" + cadastradoEm +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoImagem that = (ProdutoImagem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
