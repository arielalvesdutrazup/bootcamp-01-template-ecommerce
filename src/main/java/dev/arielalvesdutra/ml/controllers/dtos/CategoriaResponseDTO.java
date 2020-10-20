package dev.arielalvesdutra.ml.controllers.dtos;

import dev.arielalvesdutra.ml.entities.Categoria;

import java.util.Objects;

public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private CategoriaResponseDTO categoriaMae;

    public CategoriaResponseDTO() {    }

    public CategoriaResponseDTO(Categoria categoria) {
        setId(categoria.getId());
        setNome(categoria.getNome());

        if (categoria.getCategoriaMae() != null) {
            setCategoriaMae(new CategoriaResponseDTO(categoria.getCategoriaMae()));
        }
    }

    public Long getId() {
        return id;
    }

    public CategoriaResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public CategoriaResponseDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public CategoriaResponseDTO getCategoriaMae() {
        return categoriaMae;
    }

    public CategoriaResponseDTO setCategoriaMae(CategoriaResponseDTO categoriaMae) {
        this.categoriaMae = categoriaMae;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaResponseDTO that = (CategoriaResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(categoriaMae, that.categoriaMae);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, categoriaMae);
    }
}
