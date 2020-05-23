package com.viniciusgomes.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.viniciusgomes.cursomc.domain.enums.EstadoPagamento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// Quando trabalhamos com classes abstratas, é necessário criar classes concretas que herdarão dela
// para que eu a torne instanciável. No entanto, ao salvar um pagamento, preciso informar para o JSON
// se o pagamento será com boleto ou com cartão, pois pagamento é uma classe abstrata. Dessa forma
// uso a anotação @JsonTypeInfo para indicar para o Json que haverá uma propriedade @type que indicará
// qual classe filha de pagamento precisará ser instanciada no ato de cadastro de um novo pagamento
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    // Sendo Um para um é necessário que o Id de pedido seja o mesmo de pagamento
    // Por isso não geramos automaticamente o Id
    @Id
    private Integer id;
    private Integer estado;


    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId // usado para mapear o id de pedido para o id de pagamento e torná-lo o mesmo
    @JsonIgnore
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        this.id = id;
        this.estado = (estado == null) ? null : estado.getCod();
        this.pedido = pedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(estado);
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado.getCod();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return id.equals(pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", estadoPagamento=" + estado +
                '}';
    }


}
