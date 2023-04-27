package co.edu.uniquindio.Microservicios_API_PF;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Pedido;
import co.edu.uniquindio.Microservicios_API_PF.excepciones.PedidoNotFoundException;
import co.edu.uniquindio.Microservicios_API_PF.servicios.PedidoServicio;
import co.edu.uniquindio.Microservicios_API_PF.servicios.TokenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    /*public ResponseEntity<Pedido> obtenerPedido(@PathVariable String id_pedido, @RequestHeader("Authorization") String authToken){*/
    public ResponseEntity<Pedido> obtenerPedido(@PathVariable String id_pedido){

        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");

        /* if (authToken == null)
        {
            LOGGER.warning("Usuario no autorizado para realizar la operación.");
            throw new WebApplicationException("Debe iniciar sesión para realizar la acción.", Response.status(Response.Status.UNAUTHORIZED)
                    .header("WWW-Authenticate", "Basic realm=\"Restricted Area\"")
                    .entity("Usuario no autorizado para realizar la operación.")
                    .build());
        }
        //Como verifico que el pedido corresponda a el usuario?
        if(!authorization.substring(7).equals(token)){
            LOGGER.warning("Usuario no posee permisos para realizar la operación.");
            throw new WebApplicationException("Usuario no posee permisos para realizar la operación.", Response.Status.FORBIDDEN);
        }*/

        return new ResponseEntity<>(getAndVerify(id_pedido), HttpStatus.OK);
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
    /*public ResponseEntity<Pedido> tracer (@PathVariable("id_pedido") String id_pedido, @RequestHeader("Authorization") String authToken)*/
    public ResponseEntity<Pedido> tracer (@PathVariable("id_pedido") String id_pedido)
    {
        LOGGER.info("Operacion obteniendo ubicaciones");
        Objects.requireNonNull(id_pedido,"El id del pedido no puede ser nulo");

        /*if (authToken == null)
        {
            LOGGER.warning("Usuario no autorizado para realizar la operación.");
            throw new WebApplicationException("Debe iniciar sesión para realizar la acción.", Response.status(Response.Status.UNAUTHORIZED)
                    .header("WWW-Authenticate", "Basic realm=\"Restricted Area\"")
                    .entity("Usuario no autorizado para realizar la operación.")
                    .build());
        }*/
        //Como verifico que el pedido corresponda a el usuario?
        /*if(!authorization.substring(7).equals(token)){
            LOGGER.warning("Usuario no posee permisos para realizar la operación.");
            throw new WebApplicationException("Usuario no posee permisos para realizar la operación.", Response.Status.FORBIDDEN);
        }*/
        return new ResponseEntity<>(getRoute(id_pedido), HttpStatus.OK);
    }

    private Pedido getRoute (String id_pedido)
    {
        Optional<Pedido> pedido = pedidoServicio.findById_pedido(id_pedido);
        return pedido.orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado."));
    }

    @GetMapping("{id_pedido}/datetime_adjust")
    public ResponseEntity<String> convertirFechaEntrega(@PathVariable("id_pedido") String idPedido, @RequestHeader("ubicacion_cliente") String zonaHoraria, @RequestHeader("Authorization") String authToken) {
        System.out.println("Aquí si entré");
        Objects.requireNonNull(idPedido, "El id del pedido no puede ser nulo");

        if (authToken == null) {
            LOGGER.warning("Usuario no autorizado para realizar la operación.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "al parecer usted no está autorizado para acceder a este servicio.");
        }else if(tokenServicio.validateToken(authToken) == false){

        }
        // Aquí debes verificar que el pedido corresponda al usuario autenticado.

        System.out.println(idPedido);
        Optional<Pedido> pedido = pedidoServicio.findById_pedido(idPedido);
        System.out.println(pedido.get().getId());
        if (pedido.isPresent()) {
            String fechaEntrega = pedido.get().getFecha_entrega();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(fechaEntrega, formatter);
            ZoneId zonaHorariaActual = ZoneId.of("America/New_York");
            ZoneId zonaHorariaNueva = ZoneId.of(zonaHoraria);
            LocalDateTime nuevaFechaEntrega = localDateTime.atZone(zonaHorariaActual).withZoneSameInstant(zonaHorariaNueva).toLocalDateTime();
            return ResponseEntity.ok(nuevaFechaEntrega.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
