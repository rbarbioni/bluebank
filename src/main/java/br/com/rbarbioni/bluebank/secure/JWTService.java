package br.com.rbarbioni.bluebank.secure;

import br.com.rbarbioni.bluebank.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * Created by renan on 12/02/2017.
 */

@Service
public class JWTService {

    private final String secret;

    private final Long expirationTime;

    @Autowired
    public JWTService(@Value("secret") String secret, @Value("expiration_time") String expirationTime) {
        this.secret = secret;
        this.expirationTime = Long.valueOf(expirationTime);
    }

    private String encode(Account account) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(account.getId().toString())
                .setIssuedAt(now)
                .setIssuer(account.getNumero())
                .signWith(signatureAlgorithm, signingKey);

        if (expirationTime >= 0) {
            long expMillis = nowMillis + expirationTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return Base64.getEncoder().encodeToString(builder.compact().getBytes());
    }

    private boolean decode(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(new String(Base64.getDecoder().decode(token))).getBody();
        return claims.getId() != null && !claims.getId().isEmpty();
    }
}
