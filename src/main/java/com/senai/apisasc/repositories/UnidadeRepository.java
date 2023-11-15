package com.senai.apisasc.repositories;

import com.senai.apisasc.models.UnidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnidadeRepository extends JpaRepository<UnidadeModel, UUID> {
}