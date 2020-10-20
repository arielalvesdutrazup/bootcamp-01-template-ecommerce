package dev.arielalvesdutra.ml.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @todo Documentar
 * @todo Refatorar
 * @tod Estende ArqquivoService?
 */
@Service
public class ImagemService {
    // @todo Refetorar
    public String armazenar(MultipartFile imagem) {
        System.out.println("___ Simulando o armazenamento de imagem em disco/cloud..." + imagem);

        return getImagemLink(imagem);
    }

    // @todo Refetorar
    private String getImagemLink(MultipartFile imagem) {
        return UUID.randomUUID().toString() + getExtensaoDoArquivo(imagem);
    }

    // @todo Refetorar
    // @todo criar testes de unidade
    private String getExtensaoDoArquivo(MultipartFile arquivo) {
        var nome = arquivo.getOriginalFilename();
        return nome.substring(nome.lastIndexOf(".")).toLowerCase();
    }
}
