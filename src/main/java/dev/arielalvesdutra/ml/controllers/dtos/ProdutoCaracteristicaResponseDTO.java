package dev.arielalvesdutra.ml.controllers.dtos;

import dev.arielalvesdutra.ml.entities.ProdutoCaracteristica;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ProdutoCaracteristicaResponseDTO {

    private Long id;
    private String valor;
    private String complemento;
    private CaracteristicaResponseDTO caracteristica;

    public ProdutoCaracteristicaResponseDTO() {}

    public ProdutoCaracteristicaResponseDTO(ProdutoCaracteristica produtoCaracteristica) {
        setId(produtoCaracteristica.getId());
        setValor(produtoCaracteristica.getValor());
        setComplemento(produtoCaracteristica.getComplemento());
        setCaracteristica(new CaracteristicaResponseDTO(produtoCaracteristica.getCaracteristica()));
    }

    public Long getId() {
        return id;
    }

    public ProdutoCaracteristicaResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getValor() {
        return valor;
    }

    public ProdutoCaracteristicaResponseDTO setValor(String valor) {
        this.valor = valor;
        return this;
    }

    public String getComplemento() {
        return complemento;
    }

    public ProdutoCaracteristicaResponseDTO setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public CaracteristicaResponseDTO getCaracteristica() {
        return caracteristica;
    }

    public ProdutoCaracteristicaResponseDTO setCaracteristica(CaracteristicaResponseDTO caracteristica) {
        this.caracteristica = caracteristica;
        return this;
    }

    public static Set<ProdutoCaracteristicaResponseDTO> paraDTO(
            Set<ProdutoCaracteristica> caracteristicas) {

        return caracteristicas.stream()
                .map(ProdutoCaracteristicaResponseDTO::new)
                .collect(toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoCaracteristicaResponseDTO that = (ProdutoCaracteristicaResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(valor, that.valor) &&
                Objects.equals(complemento, that.complemento) &&
                Objects.equals(caracteristica, that.caracteristica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, complemento, caracteristica);
    }
}
