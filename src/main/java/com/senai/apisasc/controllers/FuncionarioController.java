package com.senai.apisasc.controllers;


import com.senai.apisasc.dtos.FuncionarioDto;
import com.senai.apisasc.models.FuncionarioModel;
import com.senai.apisasc.repositories.FuncionarioRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/funcionarios", produces = {"application/json"})
public class FuncionarioController {
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @GetMapping
    public ResponseEntity<List<FuncionarioModel>> listarFuncionarios() {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.findAll());
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Object> exibirFuncionario(@PathVariable(value = "idFuncionario") UUID id) {
        Optional<FuncionarioModel> funcionarioBuscado = funcionarioRepository.findById(id);

        if (funcionarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(funcionarioBuscado.get());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> cadastrarFuncionario(@RequestBody @Valid FuncionarioDto funcionarioDto) {
        if (funcionarioRepository.findByEmail(funcionarioDto.email()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse email já está cadastrado!");
        }

        FuncionarioModel funcionario = new FuncionarioModel();
        BeanUtils.copyProperties(funcionarioDto, funcionario);

        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionario));
    }

}


