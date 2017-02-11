package br.com.rbarbioni.bluebank.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by renan on 10/02/2017.
 */

@Entity
@Table( name = "ContaCorrente" )
public class ContaCorrente implements Serializable {

    private static final long serialVersionUID = -6226618365169837926L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "agencia")
    private String agencia;

    @Column(name = "conta")
    private String conta;

    @Column(name = "saldo")
    private BigDecimal saldo;

    private ContaCorrente(){
        super();
        this.createAt = new Date();
        this.saldo = BigDecimal.ZERO;
    }
    public ContaCorrente(String cpf, String agencia, String conta) {
        this();
        this.cpf = cpf;
        this.agencia = agencia;
        this.conta = conta;
    }

    public Long getId() {
        return id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void sacar(BigDecimal valor){
        this.saldo = saldo.subtract(valor);
    }

    public void depositar (BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }
}