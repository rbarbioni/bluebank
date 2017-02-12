package br.com.rbarbioni.bluebank.model;

import br.com.rbarbioni.bluebank.util.Cpf;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by renan on 10/02/2017.
 */

@Entity
@Table( name = "conta_corrente" )
public class Account implements Serializable {

    private static final long serialVersionUID = -6226618365169837926L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_at")
    private Date createAt;

    @Cpf
    @NotEmpty
    @Column(name = "cpf")
    private String cpf;

    @NotEmpty
    @Column(name = "agencia")
    private String agencia;

    @NotEmpty
    @Column(name = "numero")
    private String numero;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    private Account(){
        super();
        this.createAt = new Date();
        this.saldo = BigDecimal.ZERO;
    }
    public Account(String cpf, String agencia, String numero) {
        this();
        this.cpf = cpf;
        this.agencia = agencia;
        this.numero = numero;
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

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getPassword() {
        return password;
    }

    public void sacar(BigDecimal valor){
        this.saldo = saldo.subtract(valor);
    }

    public void depositar (BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }
}