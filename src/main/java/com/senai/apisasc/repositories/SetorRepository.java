package com.senai.apisasc.repositories;

import com.senai.apisasc.models.SetorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SetorRepository extends JpaRepository<SetorModel, UUID> {
    SetorModel findByTitulo(String titulo);
}
