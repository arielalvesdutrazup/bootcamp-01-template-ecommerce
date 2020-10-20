package dev.arielalvesdutra.ml.tests.factories.dtos;

import dev.arielalvesdutra.ml.controllers.dtos.CadastraProdutoPerguntaRequestDTO;

public class CadastraProdutoPerguntaRequestDTOFactory {

    public static CadastraProdutoPerguntaRequestDTO perguntaParaPersisitr() {
        return new CadastraProdutoPerguntaRequestDTO()
                .setTitulo("Tem este produto na cor azul?");
    }
}
