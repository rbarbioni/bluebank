package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.exception.BlueBankException;
import br.com.rbarbioni.bluebank.model.ContaCorrente;
import br.com.rbarbioni.bluebank.model.dto.ContaCorrenteTransferenciaDto;
import br.com.rbarbioni.bluebank.repository.ContaCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ContaCorrente findUnique(String cpf, String agencia, String conta){

        ContaCorrente contaCorrente = this.contaCorrenteRepository.findUnique(cpf, agencia, conta);
        if(contaCorrente == null){
            throw new BlueBankException(HttpStatus.BAD_REQUEST.value(), String.format("ContaCorrente inválida [cpf: %s, agencia: %s, conta: %s]", cpf, agencia, conta));
        }
        return contaCorrente;
    }

    @Transactional
    public ContaCorrente transferir (ContaCorrenteTransferenciaDto contaCorrenteTransferenciaDto){

        ContaCorrente contaOrigem = this.findUnique(
                contaCorrenteTransferenciaDto.getContaOrigem().getCpf(),
                contaCorrenteTransferenciaDto.getContaOrigem().getAgencia(),
                contaCorrenteTransferenciaDto.getContaOrigem().getConta()
                );

        contaOrigem = sacar(contaOrigem, contaCorrenteTransferenciaDto.getValor());

        this.save(contaOrigem);

        ContaCorrente contaDestino = this.findUnique(
                contaCorrenteTransferenciaDto.getContaDestino().getCpf(),
                contaCorrenteTransferenciaDto.getContaDestino().getAgencia(),
                contaCorrenteTransferenciaDto.getContaDestino().getConta()
        );

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
            throw new BlueBankException(HttpStatus.BAD_REQUEST.value(), String.format("Saldo insuficiente [cpf: %s, agência: %s, conta: %s, valor %d]",
                    contaCorrente.getCpf(), contaCorrente.getAgencia(), contaCorrente.getConta(), valor));
        }

        contaCorrente.sacar(BigDecimal.valueOf(valor));

        return this.save(contaCorrente);
    }
}
