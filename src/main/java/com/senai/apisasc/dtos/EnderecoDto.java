package com.senai.apisasc.dtos;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDto(
        @NotBlank String cep,
        @NotBlank String logradouro,
        @NotBlank String numero,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String estado
) {
}
