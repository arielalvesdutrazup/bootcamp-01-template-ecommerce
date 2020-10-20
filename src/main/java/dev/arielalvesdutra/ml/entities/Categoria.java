package dev.arielalvesdutra.ml.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome não pode ser vazio!")
    private String nome;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();

    @ManyToOne
    private Categoria categoriaMae;
    @OneToMany(mappedBy = "categoria",  cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Produto> produtos =  new HashSet<>();

    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public Categoria setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Categoria setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Categoria getCategoriaMae() {
        return categoriaMae;
    }

    public Categoria setCategoriaMae(Categoria categoriaMae) {
        this.categoriaMae = categoriaMae;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public Categoria setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Categoria setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cadastradoEm=" + cadastradoEm +
                '}';
    }
}
