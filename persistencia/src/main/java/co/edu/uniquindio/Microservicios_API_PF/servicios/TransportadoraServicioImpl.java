package co.edu.uniquindio.Microservicios_API_PF.servicios;

import co.edu.uniquindio.Microservicios_API_PF.repositorios.TransportadoraRepo;
import org.springframework.stereotype.Service;

@Service
public class TransportadoraServicioImpl implements TransportadoraServicio{

    private final TransportadoraRepo transportadoraRepo;

   public TransportadoraServicioImpl(TransportadoraRepo transportadoraRepo)
   {
       this.transportadoraRepo = transportadoraRepo;
   }

}
