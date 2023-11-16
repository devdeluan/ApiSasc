package com.senai.apisasc.controllers;

import com.senai.apisasc.dtos.SetorDto;
import com.senai.apisasc.repositories.SetorRepository;
import com.senai.apisasc.models.SetorModel;
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
@RequestMapping(value = "/setor", produces = {"application/json"})
public class SetorController {
    @Autowired
    SetorRepository setorRepository;

    @GetMapping
    public ResponseEntity <List<SetorModel>> listarSetor() {
        return ResponseEntity.status(HttpStatus.OK).body(setorRepository.findAll());
    }

    @GetMapping("/{idSetor}")
    public ResponseEntity<Object> exibirSetor(@PathVariable(value = "idSetor") UUID id) {
        Optional<SetorModel> setorBuscando = setorRepository.findById(id);

        if (setorBuscando.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setor nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(setorBuscando.get());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> cadastrarSetor(
            @RequestParam("titulo") String titulo,
            @ModelAttribute SetorDto setorDto)
    {

        if (setorRepository.findByTitulo(titulo) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este Setor já está cadastrado!");
        }

        SetorModel novoFabricante = new SetorModel();
        novoFabricante.setTitulo(titulo);
        SetorModel setorSalvo = setorRepository.save(novoFabricante);

        if (setorSalvo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível cadastrar o Setor");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(setorSalvo);
    }


    @PutMapping(value = "/{idSetor}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> editarFabricante(@PathVariable(value = "idSetor") UUID id, @RequestBody @Valid SetorDto setorDto) {
        Optional<SetorModel> setorBuscando = setorRepository.findById(id);

        if (setorBuscando.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setor nao encontrado");
        }

        SetorModel setor = setorBuscando.get();
        BeanUtils.copyProperties(setorDto, setor);

        return ResponseEntity.status(HttpStatus.CREATED).body(setorRepository.save(setor));
    }


    @DeleteMapping("/{idSetor}")
    public ResponseEntity<Object> deleterSetor(@PathVariable(value = "idSetor") UUID id) {
        Optional<SetorModel> setorBuscando = setorRepository.findById(id);

        if (setorBuscando.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setor nao encontrado");
        }

        setorRepository.delete(setorBuscando.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Setor deletado com sucesso");
    }

}
