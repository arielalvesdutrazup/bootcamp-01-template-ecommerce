package dev.arielalvesdutra.ml.tests.factories.dtos;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarProdutoOpiniaoRequestDTO;

public class CadastrarProdutoOpiniaoRequestDTOFactory {

    public static CadastrarProdutoOpiniaoRequestDTO opiniaoValida() {
        return new CadastrarProdutoOpiniaoRequestDTO()
                .setNota(5)
                .setTitulo("Produto excelente! Recomendo")
                .setDescricao("Produto resolvel a necessidade. Está de acordo com o esperado.");
    }
}
