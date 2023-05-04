package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Ubicacion;
import co.edu.uniquindio.Microservicios_API_PF.repositorios.UbicacionRepo;
import org.springframework.stereotype.Service;

@Service
public class UbicacionServicioImpl implements UbicacionServicio{

    private final UbicacionRepo ubicacionRepo;

    public UbicacionServicioImpl (UbicacionRepo ubicacionRepo)
    {
        this.ubicacionRepo = ubicacionRepo;
    }

    @Override
    public void save(Ubicacion ubicacion) {
        ubicacionRepo.save(ubicacion);
    }
}
