package co.edu.uniquindio.Microservicios_API_PF.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Credential;

import java.util.Optional;

@Repository
public interface CredentialRepo extends JpaRepository<Credential, String> {

    Optional<Credential> findByUsername(String username);
}
