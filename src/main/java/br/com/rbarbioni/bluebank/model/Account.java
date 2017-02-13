package br.com.rbarbioni.bluebank.model;

import br.com.rbarbioni.bluebank.util.Cpf;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Created by renan on 10/02/2017.
 */

@Entity
@Table( name = "account" )
@EntityListeners(AuditingEntityListener.class)
public class Account implements Serializable, UserDetails, Authentication {

    private static final long serialVersionUID = -6226618365169837926L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private Date modifieldDate;

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

    @Transient
    private String token;

    private Account(){
        super();
        this.createdDate = new Date();
        this.saldo = BigDecimal.ZERO;
    }

    public Account(String cpf, String agencia, String numero) {
        this();
        this.cpf = cpf;
        this.agencia = agencia;
        this.numero = numero;
    }

    public Account(String cpf, String agencia, String numero, String password) {
        this(cpf, agencia, numero);
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
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

    public Date getModifieldDate() {
        return modifieldDate;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public Object getCredentials() {
        return null;
    }

    @JsonIgnore
    @Override
    public Object getDetails() {
        return null;
    }

    @JsonIgnore
    @Override
    public Object getPrincipal() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @JsonIgnore
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.cpf;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void sacar(BigDecimal valor){
        this.saldo = saldo.subtract(valor);
    }

    public void depositar (BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null){
            return false;
        }

        if(!(obj instanceof Account)){
            return false;
        }

        Account account = (Account) obj;

        return this.cpf.equals(account.getCpf()) && this.agencia.equals(account.getAgencia()) && this.numero.equals(account.getNumero());
    }
}