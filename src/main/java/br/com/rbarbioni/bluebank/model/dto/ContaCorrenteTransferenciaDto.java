package br.com.rbarbioni.bluebank.model.dto;

import br.com.rbarbioni.bluebank.model.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan on 10/02/2017.
 */
public class ContaCorrenteTransferenciaDto {

    @JsonProperty("conta_origen")
    private ContaCorrente contaOrigem;

    @JsonProperty("conta_destino")
    private ContaCorrente contaDestino;

    @JsonProperty("valor")
    Double valor;

    private ContaCorrenteTransferenciaDto() {
        super();
    }

    public ContaCorrenteTransferenciaDto(ContaCorrente contaOrigem, ContaCorrente contaDestino, Double valor) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
    }

    public ContaCorrente getContaOrigem() {
        return contaOrigem;
    }

    public ContaCorrente getContaDestino() {
        return contaDestino;
    }

    public Double getValor() {
        return valor;
    }
}
