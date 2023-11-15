package com.senai.apisasc.controllers;

import com.senai.apisasc.dtos.EnderecoDto;
import com.senai.apisasc.models.EnderecoModel;
import com.senai.apisasc.repositories.EnderecoRepository;
import jakarta.validation.Valid;
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
@RequestMapping(value = "/endereco", produces = {"application/json"})
public class EnderecoController {
    @Autowired
    EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<EnderecoModel>> listarEndereco() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<Object> exibirEndereco(@PathVariable(value = "idEndereco") UUID id) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(enderecoBuscado.get());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> cadastrarEndereco(@ModelAttribute @Valid EnderecoDto enderecoDto) {
        if (enderecoRepository.findByCep(enderecoDto.cep()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse cep ja esta cadastrado!");
        }

        EnderecoModel endereco = new EnderecoModel();
        BeanUtils.copyProperties(enderecoDto, endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(endereco));
    }

    @PutMapping(value =  "/{idEndereco}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> editarEndereco(@PathVariable(value = "idEndereco") UUID id, @ModelAttribute @Valid EnderecoDto enderecoDto) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco nao encontrado");
        }

        EnderecoModel endereco = enderecoBuscado.get();
        BeanUtils.copyProperties(enderecoDto, endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(endereco));
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Object> deleterEndereco(@PathVariable(value = "idEndereco") UUID id) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereco nao encontrado");
        }

        enderecoRepository.delete(enderecoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario deletado com sucesso");
    }
}
