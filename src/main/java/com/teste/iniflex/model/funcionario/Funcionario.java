package com.teste.iniflex.model.funcionario;

import com.teste.iniflex.model.pessoa.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "funcionario")
@PrimaryKeyJoinColumn(name = "id")
public class Funcionario extends Pessoa {

    @Column(nullable = false)
    private BigDecimal salario;

    @Column(nullable = false)
    private String funcao;

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(salario, that.salario) && Objects.equals(funcao, that.funcao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salario, funcao);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "salario=" + salario +
                ", funcao='" + funcao + '\'' +
                '}';
    }
}
