package dev.arielalvesdutra.ml.tests.unit.entities;

import dev.arielalvesdutra.ml.entities.Produto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoTest {

    @Test
    public void geraUrl_comNomeComEspacosEId_deveGerarUrlSemEspacosEComHifenEMinuscula() {
        var produto = new Produto()
                .setNome("Nome do produto - novo modelo")
                .setId(1L);

        produto.geraUrl();

        assertThat(produto.getUrl()).isEqualTo("1nome-do-produto---novo-modelo");
    }
}
