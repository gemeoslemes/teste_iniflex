package com.teste.iniflex.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioDTO(
        @NotBlank
        String nome,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @NotNull
        LocalDate dataNascimento,

        @NotNull
        BigDecimal salario,

        @NotBlank
        String funcao) {


}
