package dev.arielalvesdutra.ml.tests.factories.dtos;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarUsuarioRequestDTO;

/**
 * Classe auxiliar para testes,.
 */
public class CadastrarUsuarioRequestDTOFactory {

    public static CadastrarUsuarioRequestDTO paraPersistir() {
        return usuarioExemplo();
    }

    public static CadastrarUsuarioRequestDTO usuarioExemplo() {
        return new CadastrarUsuarioRequestDTO()
                .setLogin("email@examplo.com")
                .setSenha("sZDT4qAcUZoQz1");
    }

    public static CadastrarUsuarioRequestDTO segundoUsuarioExemplo() {
        return new CadastrarUsuarioRequestDTO()
                .setLogin("teste@teste.com.br")
                .setSenha("sabababAcUZoQrwwb");
    }
}
