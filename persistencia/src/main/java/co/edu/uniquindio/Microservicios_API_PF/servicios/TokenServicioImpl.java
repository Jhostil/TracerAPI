package co.edu.uniquindio.Microservicios_API_PF.servicios;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Token;
import co.edu.uniquindio.Microservicios_API_PF.repositorios.TokenRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.TransactionRequiredException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;


@Service
public class TokenServicioImpl implements TokenServicio{

    private final TokenRepo tokenRepository;
    private final String secretKey;
    private final long expirationTime;

    public TokenServicioImpl(TokenRepo tokenRepository,
                             @Value("jwt.expiration-time") long expirationTime) {
        this.tokenRepository = tokenRepository;
        this.secretKey = System.getenv("JWT_SECRET");
        this.expirationTime = expirationTime;
    }

    public String generateToken(String subject) {
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(expirationTime);
        String tokenString = Jwts.builder()
                .setSubject(subject)
                .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
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
            if (token.getExpirationDate().isAfter(LocalDateTime.now())) {
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
        }catch (JwtException e){
            // en caso de que el token no sea válido
            throw new JwtException("Token inválido: " + e.getMessage());
        }
        return subject;
    }

    public Optional<Token> findByTokenString(String tokenString) {
        return tokenRepository.findByTokenString(tokenString);
    }

    public void save(Token token) {
        try {
            tokenRepository.save(token); // intentamos guardar el pedido en la base de datos
        } catch (TransactionRequiredException e) {
            // si ocurre una excepción de tipo TransactionRequiredException, la relanzamos como RuntimeException
            throw new RuntimeException(e);
        }
    }

}