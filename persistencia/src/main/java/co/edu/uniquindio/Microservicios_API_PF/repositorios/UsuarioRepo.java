package co.edu.uniquindio.Microservicios_API_PF.repositorios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
}
