package br.com.rbarbioni.bluebank.model.dto;

import br.com.rbarbioni.bluebank.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan on 10/02/2017.
 */
public class AccountTransferDto {

    @JsonProperty("source")
    private Account source;

    @JsonProperty("destination")
    private Account destination;

    @JsonProperty("amount")
    Double amount;

    private AccountTransferDto() {
        super();
    }

    public AccountTransferDto(Account source, Account destination, Double amount) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    public Account getSource() {
        return source;
    }

    public Account getDestination() {
        return destination;
    }

    public Double getAmount() {
        return amount;
    }
}
