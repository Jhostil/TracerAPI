package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AgregarEstado {

    private UsuarioDTO usuario;

    private EnvioDTO envio;

    private EstadoDTO estado;

    private String id_pedido;

    private Response response;
    @Given("Yo soy un usuario que se encuentra autenticado en el sistema")
    public void yoSoyUnUsuarioQueSeEncuentraAutenticadoEnElSistema() {
        usuario = UsuarioDTO
                .builder()
                .usuario("Karen")
                .clave("karen")
                .build();
    }

    @And("En el servidor existe un pedido con id {string}")
    public void enElServidorExisteUnPedidoConId(String id_pedido) {
        this.id_pedido = id_pedido;
        List<EstadoDTO> estados = new ArrayList<>();
        List<UbicacionDTO> ubicaciones = new ArrayList<>();
        estados.add(new EstadoDTO(id_pedido,"llego a New York",DescripcionDTO.EN_BODEGA));
        envio = EnvioDTO
                .builder()
                .id(id_pedido)
                .estado(estados)
                .ubicaciones(ubicaciones)
                .fecha_envio("10/8/2022/05:00")
                .fecha_entrega("")
                .build();
        System.out.println("Se creó el pedido: " + envio.toString());
    }

    @When("Hago el llamado del servicio de la Api agregar estado y le envio el estado")
    public void hagoElLlamadoDelServicioDeLaApiAgregarEstadoYLeEnvioElEstado() {
        estado = EstadoDTO
                .builder()
                .descripcion(DescripcionDTO.EN_BODEGA)
                .detalle("El producto llego a Santiago de Chile")
                .id_pedido(id_pedido)
                .build();
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .body(envio)
                .post("http://localhost:8080/pedidos");

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .body(estado)
                .queryParam(id_pedido)
                .patch("http://localhost:8080/pedidos/" + estado.getId_pedido());
    }

    @Then("Recibo un estado {int}")
    public void reciboUnEstado(int status) {
        response.then().statusCode(status);
    }

    @And("En el servidor no existe un pedido con id {string}")
    public void enElServidorNoExisteUnPedidoConId(String id_pedido) {
        this.id_pedido = id_pedido;
        List<EstadoDTO> estados = new ArrayList<>();
        List<UbicacionDTO> ubicaciones = new ArrayList<>();
        estados.add(new EstadoDTO(id_pedido,"llego a New York",DescripcionDTO.EN_BODEGA));
        envio = EnvioDTO
                .builder()
                .id(id_pedido+"a")
                .estado(estados)
                .ubicaciones(ubicaciones)
                .fecha_envio("10/8/2023/05:00")
                .fecha_entrega("")
                .build();
        System.out.println("Se creó el pedido: " + envio.toString());
    }
}
