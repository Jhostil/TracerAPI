package co.edu.uniquindio.Microservicios_API_PF;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Estado;
import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;
import co.edu.uniquindio.Microservicios_API_PF.excepciones.PedidoNotFoundException;
import co.edu.uniquindio.Microservicios_API_PF.servicios.PedidoServicio;
import co.edu.uniquindio.Microservicios_API_PF.servicios.TokenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/pedidos")
public class PedidoController {


    private static final Logger LOGGER = Logger.getLogger(PedidoController.class.getName());

    @RequestMapping(value = "/endpoint", method = RequestMethod.GET)
    public String getDemo() {
        return "Hello world!";
    }
    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private TokenServicio tokenServicio;


    @PostMapping
    public void create (@RequestBody Pedido pedido)
    {
        pedidoServicio.save(pedido);
    }



    @GetMapping("{id_pedido}")
    public ResponseEntity<Pedido> obtenerPedido(@PathVariable String id_pedido){

        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");

        return new ResponseEntity<>(getAndVerify(id_pedido), HttpStatus.OK);
    }

    @PatchMapping("{id_pedido}")
    private ResponseEntity<String> agregarEstado(@PathVariable String id_pedido, @RequestBody Estado estado) {
        LOGGER.info("Operacion agregando nuevo estado");
        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");
        try {
            Pedido pd = getAndVerify(id_pedido);
            pd.getEstado().add(estado);
            estado.setId(id_pedido);
            estado.setPedido(pd);
            guardarEstado(estado);
            pedidoServicio.save(pd);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (PedidoNotFoundException pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pe.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    private void guardarEstado(Estado estado) {
        pedidoServicio.saveEstado(estado);
    }
    @GetMapping("{id_pedido}/time")
    private ResponseEntity<String> estimarFechaEntrega(@PathVariable String id_pedido) {
        LOGGER.info("Operacion estima fecha de entrega de un producto");
        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");
        try {
            Pedido pd = getAndVerify(id_pedido);

            //Ejemplo de como se debe manejar la fecha "Fri, 07 Aug 2020 18:00:00 +0000";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss Z", Locale.ROOT);
            OffsetDateTime parsedDate = OffsetDateTime.parse(pd.getFecha_envio(), formatter);
            parsedDate = parsedDate.plusDays(10L);
            pd.setFecha_entrega(parsedDate.toString());
            pedidoServicio.save(pd);
            return new ResponseEntity<>(parsedDate.toString(),HttpStatus.OK);
        }catch (PedidoNotFoundException pe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pe.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
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

    @GetMapping("{id_pedido}/datetime_adjust")
    public ResponseEntity<String> convertirFechaEntrega(@PathVariable("id_pedido") String idPedido, @RequestHeader("ubicacion_cliente") String zonaHoraria) {
        System.out.println("Aquí si entré");
        Objects.requireNonNull(idPedido, "El id del pedido no puede ser nulo");

        // Aquí debes verificar que el pedido corresponda al usuario autenticado.

        System.out.println(idPedido);
        Optional<Pedido> pedido = pedidoServicio.findById_pedido(idPedido);
        System.out.println(pedido.get().getId());
        if (pedido.isPresent()) {
            System.out.println("if");
            String fechaEntrega = pedido.get().getFecha_entrega();
            System.out.println(fechaEntrega);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(fechaEntrega, formatter);
            System.out.println(localDateTime);
            ZoneId zonaHorariaActual = ZoneId.of("America/New_York");
            ZoneId zonaHorariaNueva = ZoneId.of(zonaHoraria);
            LocalDateTime nuevaFechaEntrega = localDateTime.atZone(zonaHorariaActual).withZoneSameInstant(zonaHorariaNueva).toLocalDateTime();
            return ResponseEntity.ok(nuevaFechaEntrega.toString());
        } else {
            System.out.println("else");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
