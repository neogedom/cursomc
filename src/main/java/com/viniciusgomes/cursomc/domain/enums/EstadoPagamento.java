package com.viniciusgomes.cursomc.domain.enums;

public enum EstadoPagamento {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int cod;
    private String descricao;

    private EstadoPagamento(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum (Integer cod) {
        if (cod == null) {
            return null;
        }

        // Pega cada valor no enum EstadoPagamento
        for (EstadoPagamento x: EstadoPagamento.values()) {
            if(cod.equals(x)) { // compara com o valor que veio no argumento
                return x; // retorna se o valor for o mesmo
            }
        }

        throw new IllegalArgumentException("Id inválido " + cod);
    }
}
