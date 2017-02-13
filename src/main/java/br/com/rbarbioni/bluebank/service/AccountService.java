package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.exception.BlueBankException;
import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.AccountTransferDto;
import br.com.rbarbioni.bluebank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by renan on 10/02/2017.
 */

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save (final Account account){
        return this.accountRepository.save(account);
    }

    public Account findUnique(String cpf, String agencia, String numero){

        Account account = this.accountRepository.findUnique(cpf, agencia, numero);
        if(account == null){
            throw new BlueBankException(HttpStatus.BAD_REQUEST.value(), "Conta inválida");
        }
        return account;
    }

    @Transactional
    public Account transfer(AccountTransferDto accountTransferDto){

        Account source = this.findUnique(
                accountTransferDto.getSource().getCpf(),
                accountTransferDto.getSource().getAgencia(),
                accountTransferDto.getSource().getNumero()
                );

        Account destination = this.findUnique(
                accountTransferDto.getDestination().getCpf(),
                accountTransferDto.getDestination().getAgencia(),
                accountTransferDto.getDestination().getNumero()
        );

        if(source.equals(destination)){
            throw new BlueBankException(HttpStatus.BAD_REQUEST.value(), "Não é possivel realizar a transferência para a própria conta");
        }

        source = sacar(source, accountTransferDto.getAmount());

        this.save(source);



        destination = depositar(destination, accountTransferDto.getAmount());

        this.save(destination);

        return source;
    }

    @Transactional
    public Account depositar(Account account, Double valor){

        account.depositar(BigDecimal.valueOf(valor));
        return save(account);
    }

    @Transactional
    public Account sacar(Account account, Double valor){

        if(account.getSaldo().doubleValue() < valor){
            throw new BlueBankException(HttpStatus.BAD_REQUEST.value(), String.format("Saldo insuficiente [saldo: %s]", NumberFormat.getCurrencyInstance().format(account.getSaldo())));
        }

        account.sacar(BigDecimal.valueOf(valor));

        return this.save(account);
    }
}
