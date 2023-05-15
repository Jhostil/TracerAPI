package co.edu.uniquindio.Microservicios_API_PF;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Estado;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Transportadora;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Ubicacion;
import co.edu.uniquindio.Microservicios_API_PF.excepciones.PedidoNotFoundException;
import co.edu.uniquindio.Microservicios_API_PF.servicios.PedidoServicio;
import co.edu.uniquindio.Microservicios_API_PF.servicios.TokenServicio;
import co.edu.uniquindio.Microservicios_API_PF.servicios.TransportadoraServicio;
import co.edu.uniquindio.Microservicios_API_PF.servicios.UbicacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/pedidos")
public class PedidoController {


    private static final Logger LOGGER = Logger.getLogger(PedidoController.class.getName());

    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private TokenServicio tokenServicio;
    @Autowired
    private TransportadoraServicio transportadoraServicio;

    @Autowired
    private UbicacionServicio ubicacionServicio;


    @PostMapping
    public void create (@RequestBody Pedido pedido)
    {
        pedidoServicio.save(pedido);
        System.out.println(pedido.getUbicaciones());
        if (!pedido.getUbicaciones().isEmpty())
        {
            guardarUbicaciones (pedido);
        }
    }

    public void guardarUbicaciones (Pedido pedido)
    {
        for (Ubicacion ubicacion: pedido.getUbicaciones()) {
            ubicacion.setPedido(pedido);
            ubicacionServicio.save(ubicacion);
        }
    }

    @GetMapping("{id_pedido}")
    public ResponseEntity<Pedido> obtenerPedido(@PathVariable String id_pedido){

        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");

        return new ResponseEntity<>(getAndVerify(id_pedido), HttpStatus.OK);
    }

    @PatchMapping("{id_pedido}/addEstado")
    private ResponseEntity<String> agregarEstado(@PathVariable String id_pedido, @RequestBody Estado estado) {
        LOGGER.info("Operacion agregando nuevo estado");
        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");
        try {
            Pedido pd = getAndVerify(id_pedido);
            pd.getEstado().add(estado);
            estado.setPedido(pd);
            guardarEstado(estado);
            pedidoServicio.save(pd);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (PedidoNotFoundException pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pe.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    private void guardarEstado(Estado estado) {
        LOGGER.info("Se guardó el estado del producto");
        pedidoServicio.saveEstado(estado);
    }
    @GetMapping("{id_pedido}/time")
    private ResponseEntity<Pedido> estimarFechaEntrega(@PathVariable String id_pedido) {
        LOGGER.info("Operacion estima fecha de entrega de un producto");
        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");
        Pedido pd = getAndVerify(id_pedido);
        try {
            //Ejemplo de como se debe manejar la fecha dd/mm/yyyy/hh:mm
            String[] fecha = pd.getFecha_envio().split("/");
            LocalDateTime time = LocalDateTime.of(Integer.parseInt(fecha[2]),Integer.parseInt(fecha[1]),Integer.parseInt(fecha[0]),0,0);
            time = time.plusDays(10);
            pd.setFecha_entrega(time.getDayOfMonth()+"/"+time.getMonthValue()+"/"+time.getYear()+"/"+fecha[3]);
            System.out.println(pd.getFecha_entrega()+"\n"+pd.getFecha_envio());
            pedidoServicio.save(pd);
            return new ResponseEntity<>(pd,HttpStatus.OK);
        }catch (PedidoNotFoundException pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pd);
        }
    }

    private Pedido getAndVerify(String id_pedido){
        Optional<Pedido> pedido = pedidoServicio.findById_pedido(id_pedido);
        LOGGER.info("Operacion buscando");
        return pedido.orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado."));

    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<String> handlePedidoNotFoundException(PedidoNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @GetMapping("{id_pedido}/tracer")
    public ResponseEntity<Pedido> tracer (@PathVariable("id_pedido") String id_pedido)
    {
        LOGGER.info("Operacion obteniendo ubicaciones");
        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");
        return new ResponseEntity<>(getRoute(id_pedido), HttpStatus.OK);
    }

    private Pedido getRoute (String id_pedido)
    {
        Optional<Pedido> pedido = pedidoServicio.findById_pedido(id_pedido);
        return pedido.orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado."));
    }


    @GetMapping("{id_pedido}/transportadoras")
    public ResponseEntity<?> obtenerTransportadora (@PathVariable("id_pedido") String id_pedido)
    {
        LOGGER.info("Operacion obtener información de la transportadora");
        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");
        try {

            Pedido pd = getAndVerify(id_pedido);

            return new ResponseEntity<>(pd,HttpStatus.OK);
        } catch (PedidoNotFoundException pe) {
            return new ResponseEntity<>(getRoute(id_pedido), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Hubo un error, por favor intente de nuevo",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id_pedido}/datetime_adjust")
    public ResponseEntity<String> convertirFechaEntrega(@PathVariable("id_pedido") String idPedido, @RequestParam("zona_horaria") String zonaHoraria) {
        Objects.requireNonNull(idPedido, "El id del pedido no puede ser nulo");
        System.out.println(idPedido);
        Optional<Pedido> pedido = pedidoServicio.findById_pedido(idPedido);
        if (pedido.isPresent()) {
            String fechaEntrega = pedido.get().getFecha_entrega();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy/HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(fechaEntrega, formatter);
            System.out.println(localDateTime);
            ZoneId zonaHorariaOriginal = ZoneId.of("America/New_York");
            ZoneId zonaHorariaNueva = ZoneId.of(zonaHoraria);
            ZonedDateTime zonedDateTimeOriginal = localDateTime.atZone(zonaHorariaOriginal);
            ZonedDateTime zonedDateTimeNueva = zonedDateTimeOriginal.withZoneSameInstant(zonaHorariaNueva);
            LocalDateTime nuevaFechaEntrega = zonedDateTimeNueva.toLocalDateTime();
            String fechaFormateada = nuevaFechaEntrega.format(formatter);
            System.out.println(nuevaFechaEntrega);
            return ResponseEntity.ok(fechaFormateada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
