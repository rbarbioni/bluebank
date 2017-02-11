package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.model.ContaCorrente;
import br.com.rbarbioni.bluebank.model.dto.ContaCorrenteTransferenciaDto;
import br.com.rbarbioni.bluebank.repository.ContaCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by renan on 10/02/2017.
 */

@Service
public class ContaCorrenteService {

    private final ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    public ContaCorrenteService(ContaCorrenteRepository contaCorrenteRepository) {
        this.contaCorrenteRepository = contaCorrenteRepository;
    }

    public ContaCorrente save (final ContaCorrente contaCorrente){
        return this.contaCorrenteRepository.save(contaCorrente);
    }

    public ContaCorrente findUnique(ContaCorrente contaCorrente){
        contaCorrente = this.contaCorrenteRepository.findUnique(contaCorrente);
        if(contaCorrente == null){
            throw new IllegalStateException(String.format("ContaCorrente inválida [cpf: %s, agencia: %s, conta: %s]",
                    contaCorrente.getAgencia(), contaCorrente.getConta(), contaCorrente.getConta()));
        }
        return contaCorrente;
    }

    @Transactional
    public ContaCorrente transferir (ContaCorrenteTransferenciaDto contaCorrenteTransferenciaDto){

        ContaCorrente contaOrigem = this.findUnique(contaCorrenteTransferenciaDto.getContaOrigem());

        contaOrigem = sacar(contaOrigem, contaCorrenteTransferenciaDto.getValor());

        this.save(contaOrigem);

        ContaCorrente contaDestino = this.findUnique(contaCorrenteTransferenciaDto.getContaDestino());

        contaDestino = depositar(contaDestino, contaCorrenteTransferenciaDto.getValor());

        this.save(contaDestino);

        return contaDestino;
    }

    @Transactional
    public ContaCorrente depositar(ContaCorrente contaCorrente, Double valor){

        contaCorrente.depositar(BigDecimal.valueOf(valor));
        return save(contaCorrente);
    }

    @Transactional
    public ContaCorrente sacar(ContaCorrente contaCorrente, Double valor){

        if(contaCorrente.getSaldo().doubleValue() < valor){
            throw new IllegalStateException(String.format("Saldo insuficiente [cpf: %s, agência: %s, conta: %s, valor %d]",
                    contaCorrente.getCpf(), contaCorrente.getAgencia(), contaCorrente.getConta(), valor));
        }

        contaCorrente.sacar(BigDecimal.valueOf(valor));

        return this.save(contaCorrente);
    }
}
