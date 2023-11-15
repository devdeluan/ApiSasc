package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;

public record UnidadeDto(
        @NotBlank String nome
) {
}
