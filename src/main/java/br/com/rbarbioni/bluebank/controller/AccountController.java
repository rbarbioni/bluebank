package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.dto.AccountTransferDto;
import br.com.rbarbioni.bluebank.service.AccountService;
import br.com.rbarbioni.bluebank.util.Cpf;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Created by renan on 10/02/2017.
 */

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = {RequestMethod.GET})
    public Account getConta (
            @NotNull @NotEmpty @Cpf @RequestParam String cpf,
            @NotNull @NotEmpty @RequestParam String agencia,
            @NotNull @NotEmpty @RequestParam String numero){
        return this.accountService.findUnique(cpf, agencia, numero);
    }

    @RequestMapping(value = "transferir", method = {RequestMethod.POST})
    public Account depositar (
            @NotNull @RequestBody AccountTransferDto accountTransferDto){

        return this.accountService.transferir(accountTransferDto);
    }
}
