package com.teste.iniflex.records;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

public class FuncionarioSalarioMinimoVO extends RepresentationModel<FuncionarioSalarioMinimoVO> {

    private String nome;

    private BigDecimal salariosMinimos;

    public FuncionarioSalarioMinimoVO() {}

    public FuncionarioSalarioMinimoVO(String nome, BigDecimal salariosMinimos) {
        this.nome = nome;
        this.salariosMinimos = salariosMinimos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FuncionarioSalarioMinimoVO that = (FuncionarioSalarioMinimoVO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(salariosMinimos, that.salariosMinimos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nome, salariosMinimos);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalariosMinimos() {
        return salariosMinimos;
    }

    public void setSalariosMinimos(BigDecimal salariosMinimos) {
        this.salariosMinimos = salariosMinimos;
    }
}
