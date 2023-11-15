package com.senai.apisasc.repositories;

import com.senai.apisasc.models.FabricanteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FabricanteRepository extends JpaRepository<FabricanteModel, UUID> {
    FabricanteModel findByCnpj(String cnpj);
}
