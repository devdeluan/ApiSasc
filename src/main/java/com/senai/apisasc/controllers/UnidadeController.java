package com.senai.apisasc.controllers;


import com.senai.apisasc.models.UnidadeModel;
import com.senai.apisasc.repositories.EnderecoRepository;
import com.senai.apisasc.repositories.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/unidade", produces = {"application/json"})
public class UnidadeController {
    @Autowired
    UnidadeRepository unidadeRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<UnidadeModel>> listarUnidades() {
        return ResponseEntity.status(HttpStatus.OK).body(unidadeRepository.findAll());
    }

    @GetMapping("/{idUnidade}")
    public ResponseEntity<Object> exibirUnidade(@PathVariable(value = "idUnidade")UUID id) {
        Optional<UnidadeModel> unidadeBuscada = unidadeRepository.findById(id);

        if (unidadeBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unidade nao encontrada");
        }

        return ResponseEntity.status(HttpStatus.OK).body(unidadeBuscada.get());
    }
}
