package co.edu.uniquindio.Microservicios_API_PF.repositorios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenString(String tokenString);


}