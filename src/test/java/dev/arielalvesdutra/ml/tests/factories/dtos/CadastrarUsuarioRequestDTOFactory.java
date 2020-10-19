package dev.arielalvesdutra.ml.tests.factories.dtos;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarUsuarioRequestDTO;

/**
 * Classe auxiliar para testes,.
 */
public class CadastrarUsuarioRequestDTOFactory {

    public static CadastrarUsuarioRequestDTO paraPersistir() {
        return new CadastrarUsuarioRequestDTO()
                .setLogin("email@examplo.com")
                .setSenha("sZDT4qAcUZoQz1");
    }
}
