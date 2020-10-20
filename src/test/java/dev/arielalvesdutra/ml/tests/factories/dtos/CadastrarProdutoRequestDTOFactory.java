package dev.arielalvesdutra.ml.tests.factories.dtos;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarProdutoRequestDTO;
import dev.arielalvesdutra.ml.controllers.dtos.ProdutoCaracteristicaRequestDTO;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CadastrarProdutoRequestDTOFactory {


    /**
     * @todo REFATORAR
     */
    public static CadastrarProdutoRequestDTO paraPersistir(
            Set<Long> caracteristicasIds,
            Long categoriaId) {

        Set<ProdutoCaracteristicaRequestDTO> caracteristicas = new HashSet<>();

        caracteristicasIds.forEach(id -> {
            var caracteristicaDTO = new ProdutoCaracteristicaRequestDTO()
                    .setCaracteristicaId(id)
                    .setValor("Valor da caracteristica " + id)
                    .setComplemento("Complemento da caracteristica " + id);
            caracteristicas.add(caracteristicaDTO);
        });

        return new CadastrarProdutoRequestDTO()
                .setNome("Mesa de madeira")
                .setValor(new BigDecimal("150.50"))
                .setQuantidade(100)
                .setCaracteristicas(caracteristicas)
                .setDescricao("Mesa para escritório")
                .setCategoriaId(categoriaId);
    }
}
