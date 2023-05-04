package co.edu.uniquindio.Microservicios_API_PF.repositorios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepo extends JpaRepository<Ubicacion, Integer> {
}
