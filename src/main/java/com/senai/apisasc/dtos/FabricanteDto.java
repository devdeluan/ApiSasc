package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;

public record FabricanteDto(
        @NotBlank String titulo,
        @NotBlank String cnpj
) {
}
