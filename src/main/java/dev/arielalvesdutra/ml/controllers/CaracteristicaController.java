package dev.arielalvesdutra.ml.controllers;

import dev.arielalvesdutra.ml.controllers.dtos.CadastrarCaracteristicaRequestDTO;
import dev.arielalvesdutra.ml.entities.Caracteristica;
import dev.arielalvesdutra.ml.services.CaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * 1 CaracteristicaService.java
 * 2 Caracteristica.java
 * 3 CadastrarCaracteristicaRequestDTO.java
 */
@RequestMapping("/caracteristicas")
@RestController
public class CaracteristicaController {

    @Autowired
    private CaracteristicaService caracteristicaService;

    @PostMapping
    public ResponseEntity<Long> cadastrar(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody CadastrarCaracteristicaRequestDTO requestDTO) {

        Caracteristica caracteristicaCadastrada = caracteristicaService.cadastrar(requestDTO);

        URI uri = uriBuilder.path("/caracteristicas/{id}")
                .buildAndExpand(caracteristicaCadastrada.getId())
                .toUri();

        return ResponseEntity.created(uri).body(caracteristicaCadastrada.getId());
    }
}
