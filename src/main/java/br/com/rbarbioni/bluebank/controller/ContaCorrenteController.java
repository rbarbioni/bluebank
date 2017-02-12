package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.model.ContaCorrente;
import br.com.rbarbioni.bluebank.model.dto.ContaCorrenteTransferenciaDto;
import br.com.rbarbioni.bluebank.service.ContaCorrenteService;
import br.com.rbarbioni.bluebank.util.Cpf;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Created by renan on 10/02/2017.
 */

@RestController
@RequestMapping("/api/conta_corrente")
public class ContaCorrenteController {

    private final ContaCorrenteService contaCorrenteService;

    @Autowired
    public ContaCorrenteController(ContaCorrenteService contaCorrenteService) {
        this.contaCorrenteService = contaCorrenteService;
    }

    @RequestMapping(method = {RequestMethod.GET})
    public ContaCorrente getConta (
            @NotNull @NotEmpty @Cpf @RequestParam String cpf,
            @NotNull @NotEmpty @RequestParam String agencia,
            @NotNull @NotEmpty @RequestParam String conta){
        return this.contaCorrenteService.findUnique(cpf, agencia, conta);
    }

    @RequestMapping(value = "transferir", method = {RequestMethod.POST})
    public ContaCorrente depositar (
            @NotNull @RequestBody ContaCorrenteTransferenciaDto contaCorrenteTransferenciaDto){

        return this.contaCorrenteService.transferir(contaCorrenteTransferenciaDto);
    }
}
