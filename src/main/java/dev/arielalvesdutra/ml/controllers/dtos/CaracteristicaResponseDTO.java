package dev.arielalvesdutra.ml.controllers.dtos;

import dev.arielalvesdutra.ml.entities.Caracteristica;

import java.time.OffsetDateTime;
import java.util.Objects;

public class CaracteristicaResponseDTO {

    private Long id;
    private String nome;
    private OffsetDateTime cadastradoEm;

    public CaracteristicaResponseDTO() {}

    public CaracteristicaResponseDTO(Caracteristica caracteristica) {
        setId(caracteristica.getId());
        setNome(caracteristica.getNome());
        setCadastradoEm(caracteristica.getCadastradoEm());
    }

    public Long getId() {
        return id;
    }

    public CaracteristicaResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public CaracteristicaResponseDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public CaracteristicaResponseDTO setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaracteristicaResponseDTO that = (CaracteristicaResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
