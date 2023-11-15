package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;

public record TipoFuncionarioDto(
        @NotBlank String categoria

) {
}
