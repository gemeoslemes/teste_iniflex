package com.teste.iniflex.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teste.iniflex.model.funcionario.Funcionario;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class FuncionarioSalarioTotalVO extends RepresentationModel<FuncionarioSalarioTotalVO> {

    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private BigDecimal salario;


    public FuncionarioSalarioTotalVO() {}

    public FuncionarioSalarioTotalVO(Funcionario funcionario) {
        this.nome = funcionario.getNome();
        this.salario = funcionario.getSalario().setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FuncionarioSalarioTotalVO that = (FuncionarioSalarioTotalVO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(salario, that.salario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nome, salario);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}
