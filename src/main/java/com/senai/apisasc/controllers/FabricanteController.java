package com.senai.apisasc.controllers;

import com.senai.apisasc.dtos.FabricanteDto;
import com.senai.apisasc.repositories.FabricanteRepository;
import com.senai.apisasc.models.FabricanteModel;
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
@RequestMapping(value = "/fabricante", produces = {"application/json"})
public class FabricanteController {
    @Autowired
    FabricanteRepository fabricanteRepository;

    @GetMapping
    public ResponseEntity <List<FabricanteModel>> listarFabricante() {
        return ResponseEntity.status(HttpStatus.OK).body(fabricanteRepository.findAll());
    }

    @GetMapping("/{idFabricante}")
    public ResponseEntity<Object> exibirFabricante(@PathVariable(value = "idFabricante") UUID id) {
        Optional<FabricanteModel> fabricanteBuscado = fabricanteRepository.findById(id);

        if (fabricanteBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fabricante nao encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(fabricanteBuscado.get());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> cadastrarFabricante(
            @RequestParam("titulo") String titulo,
            @RequestParam("cnpj") String cnpj,
            @ModelAttribute FabricanteDto fabricanteDto)
    {

        if (fabricanteRepository.findByCnpj(fabricanteDto.cnpj()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este CNPJ já está cadastrado!");
        }

        FabricanteModel novoFabricante = new FabricanteModel();
        novoFabricante.setTitulo(titulo);
        novoFabricante.setCnpj(cnpj);

        FabricanteModel fabricanteSalvo = fabricanteRepository.save(novoFabricante);

        if (fabricanteSalvo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível cadastrar o fabricante");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(fabricanteSalvo);
    }

@PutMapping(value = "/{idFabricante}", consumes = {MediaType.APPLICATION_JSON_VALUE})
public ResponseEntity<Object> editarFabricante(@PathVariable(value = "idFabricante") UUID id, @RequestBody @Valid FabricanteDto fabricanteDto) {
    Optional<FabricanteModel> fabricanteBuscado = fabricanteRepository.findById(id);

    if (fabricanteBuscado.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fabricante nao encontrado");
    }

    FabricanteModel fabricante = fabricanteBuscado.get();
    BeanUtils.copyProperties(fabricanteDto, fabricante);

    return ResponseEntity.status(HttpStatus.CREATED).body(fabricanteRepository.save(fabricante));
}


    @DeleteMapping("/{idFabricante}")
    public ResponseEntity<Object> deleterFabricante(@PathVariable(value = "idFabricante") UUID id) {
        Optional<FabricanteModel> fabricanteBuscado = fabricanteRepository.findById(id);

        if (fabricanteBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fabricante nao encontrado");
        }

        fabricanteRepository.delete(fabricanteBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Fabricante deletado com sucesso");
    }

}
