package com.viniciusgomes.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.viniciusgomes.cursomc.domain.enums.EstadoPagamento;

import javax.persistence.Entity;

@Entity
// Como esta é uma subclasse da classe abstrata Pagamento (que é que será salva, e que possui uma propriedade
// @type para indicar qual subclasse ela deve assumir ao ser salva), é necessário indicar qual nome
// será gravado na propriedade @type lá no JSON se a classe Propriedade assumir o tipo dessa classe
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    Integer numeroDeParcelas;

    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Integer id, EstadoPagamento estadoPagamento, Pedido pedido, Integer numeroDeParcelas) {
        super(id, estadoPagamento, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }

    @Override
    public String toString() {
        return "PagamentoComCartao{" +
                "numeroDeParcelas=" + numeroDeParcelas +
                ", id=" + super.getId() +
                ", estadoPagamento=" + super.getEstado() +
                '}';
    }
}
