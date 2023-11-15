package com.senai.apisasc.repositories;

import com.senai.apisasc.models.TipoFuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TipoFuncionarioRepository extends JpaRepository<TipoFuncionarioModel, UUID> {
}