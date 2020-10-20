package dev.arielalvesdutra.ml.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class ProdutoPergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Título é obrigatório!")
    private String titulo;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();

    @NotNull(message = "Produto é obrigatório")
    @ManyToOne
    private Produto produto;
    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne
    private Usuario usuario;

    public ProdutoPergunta() {
    }

    public Long getId() {
        return id;
    }

    public ProdutoPergunta setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitulo() {
        return titulo;
    }

    public ProdutoPergunta setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public Produto getProduto() {
        return produto;
    }

    public ProdutoPergunta setProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ProdutoPergunta setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public ProdutoPergunta setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoPergunta that = (ProdutoPergunta) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProdutoPergunta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cadastradoEm=" + cadastradoEm +
                '}';
    }
}
