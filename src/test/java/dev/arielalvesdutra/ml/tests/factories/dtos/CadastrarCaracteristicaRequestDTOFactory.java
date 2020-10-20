package dev.arielalvesdutra.ml.tests.factories.dtos;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarCaracteristicaRequestDTO;

public class CadastrarCaracteristicaRequestDTOFactory {

    public static CadastrarCaracteristicaRequestDTO paraPersistir() {
        return peso();
    }

    public static CadastrarCaracteristicaRequestDTO peso() {
        return new CadastrarCaracteristicaRequestDTO()
                .setNome("Peso");
    }

    public static CadastrarCaracteristicaRequestDTO altura() {
        return new CadastrarCaracteristicaRequestDTO()
                .setNome("Altura");
    }

    public static CadastrarCaracteristicaRequestDTO largura() {
        return new CadastrarCaracteristicaRequestDTO()
                .setNome("Largura");
    }

}
