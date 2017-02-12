package br.com.rbarbioni.bluebank.model.dto;

import java.io.Serializable;

/**
 * Created by renan on 12/02/17.
 */
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1797218726514184846L;

    private String cpf;

    private String password;

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }
}
