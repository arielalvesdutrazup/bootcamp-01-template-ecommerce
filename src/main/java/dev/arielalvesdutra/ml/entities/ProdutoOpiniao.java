package dev.arielalvesdutra.ml.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.OffsetDateTime;

@Entity
public class ProdutoOpiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota mínima é de {value}!")
    @Max(value = 5, message = "Nota máxima é de {value}!")
    private Integer nota;
    @NotBlank(message = "Título é obrigatório!")
    private String titulo;
    @Size(max = 500, message = "Descrição deve ter até {max} caracteress!")
    @NotBlank(message = "Descrição é obrigatória!")
    private String descricao;

    private OffsetDateTime cadastradoEm = OffsetDateTime.now();

    @ManyToOne
    @NotNull(message = "Produto é obrigatório!")
    private Produto produto;
    @ManyToOne
    @NotNull(message = "Usuário é obrigatório!")
    private Usuario usuario;

    public ProdutoOpiniao() {
    }

    public Long getId() {
        return id;
    }

    public ProdutoOpiniao setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNota() {
        return nota;
    }

    public ProdutoOpiniao setNota(Integer nota) {
        this.nota = nota;
        return this;
    }

    public String getTitulo() {
        return titulo;
    }

    public ProdutoOpiniao setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public ProdutoOpiniao setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public ProdutoOpiniao setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public Produto getProduto() {
        return produto;
    }

    public ProdutoOpiniao setProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ProdutoOpiniao setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    @Override
    public String toString() {
        return "ProdutoOpiniao{" +
                "id=" + id +
                ", nota=" + nota +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
