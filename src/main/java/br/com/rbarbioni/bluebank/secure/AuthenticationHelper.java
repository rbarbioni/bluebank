package br.com.rbarbioni.bluebank.secure;

import br.com.rbarbioni.bluebank.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by renan on 12/02/17.
 */

@Component
public class AuthenticationHelper {

    private final JWTService jwtService;

    public AuthenticationHelper(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    public Authentication auth (Account account) throws JsonProcessingException {
        account.setToken(this.jwtService.encode(account));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getCpf(), null, account.getAuthorities());
        authenticationToken.setDetails(account);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationToken);
        return authenticationToken;
    }
}
