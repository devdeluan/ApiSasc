package com.senai.apisasc.repositories;

import com.senai.apisasc.models.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, UUID> {
    FuncionarioModel findByEmail(String email);
}