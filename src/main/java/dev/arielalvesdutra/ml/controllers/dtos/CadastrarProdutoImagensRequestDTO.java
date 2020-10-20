package dev.arielalvesdutra.ml.controllers.dtos;

import dev.arielalvesdutra.ml.annotations.NotEmptyFiles;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CadastrarProdutoImagensRequestDTO {

    @NotNull(message = "É obrigatório enviar imagens!")
    @NotEmptyFiles(message = "É obrigatório ter uma imagem e não ter imagens vazias!")
    private List<MultipartFile> imagens;

    public CadastrarProdutoImagensRequestDTO() {
    }

    public List<MultipartFile> getImagens() {
        return imagens;
    }

    public CadastrarProdutoImagensRequestDTO setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
        return this;
    }
}
