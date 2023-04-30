package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Token;
import co.edu.uniquindio.Microservicios_API_PF.repositorios.TokenRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;

import javax.persistence.TransactionRequiredException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
@Service
public class TokenServicioImpl implements TokenServicio {

    private final TokenRepo tokenRepository;
    private final SecretKey secretKey;

    public TokenServicioImpl(TokenRepo tokenRepository) throws NoSuchAlgorithmException {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(String subject) {
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(3600000);
        String tokenString = Jwts.builder()
                .setSubject(subject)
                .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(secretKey)
                .compact();

        Token token = new Token();
        token.setTokenString(tokenString);
        token.setExpirationDate(expirationDate);
        tokenRepository.save(token);

        return tokenString;
    }

    public boolean validateToken(String tokenString) {
        Optional<Token> optionalToken = tokenRepository.findByTokenString(tokenString);
        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            LocalDateTime expirationDate = token.getExpirationDate();
            Instant expirationInstant = expirationDate.atZone(ZoneId.systemDefault()).toInstant();
            Instant currentInstant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
                if (expirationInstant.isAfter(currentInstant)) {
                    return true;
                } else {
                    tokenRepository.delete(token);
                }
        }
        return false;
    }

    public String getSubjectFromToken(String token) {
        String subject="";
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
            // en caso de que el token no sea válido
            throw new RuntimeException("Token inválido: " + e.getMessage() +"el token que ha llegado es:" + token);
        }
        return subject;
    }

}