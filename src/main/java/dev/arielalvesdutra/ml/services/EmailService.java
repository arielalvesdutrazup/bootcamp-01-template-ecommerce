package dev.arielalvesdutra.ml.services;

import dev.arielalvesdutra.ml.entities.ProdutoPergunta;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    /**
     * @todo DOCUMENTAR
     * @param produtoPergunta
     */
    public void enviarEmail(ProdutoPergunta produtoPergunta) {
        System.out.println("___ Simulando o envio de email da pergunta: " + produtoPergunta.getTitulo());
    }
}
