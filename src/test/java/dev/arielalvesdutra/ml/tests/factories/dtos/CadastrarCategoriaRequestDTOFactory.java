package dev.arielalvesdutra.ml.tests.factories.dtos;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarCategoriaRequestDTO;

public class CadastrarCategoriaRequestDTOFactory {

    public static CadastrarCategoriaRequestDTO paraPersistir() {
        return new CadastrarCategoriaRequestDTO()
                .setNome("Eletronicos");
    }

    public static CadastrarCategoriaRequestDTO paraPersitir2(Long categoriaMaeId) {
        return new CadastrarCategoriaRequestDTO()
                .setNome("Computadores")
                .setCategoriaMaeId(categoriaMaeId);
    }
}
