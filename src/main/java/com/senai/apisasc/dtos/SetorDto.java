package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;

public record SetorDto(
        @NotBlank String titulo
) {
}
