package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.DescripcionDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.EnvioDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.EstadoDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.UsuarioDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class EstimarFechaEntrega {

    private UsuarioDTO usuario;

    private EnvioDTO envio;

    private Response response;
    @Given("Soy un usuario que me encuentro autenticado en el sistema")
    public void soyUnUsuarioQueMeEncuentroAutenticadoEnElSistema() {
        usuario = UsuarioDTO
                .builder()
                .usuario("Karen")
                .clave("karen")
                .build();
    }

    @And("Ya existe un pedido en el servidor con id {string}")
    public void yaExisteUnPedidoEnElServidorConId(String id_pedido) {
        List<EstadoDTO> estados = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();
        estados.add(new EstadoDTO(id_pedido,"llego a New York", DescripcionDTO.EN_BODEGA));
        envio = EnvioDTO
                .builder()
                .id(id_pedido)
                .estado(estados)
                .fecha_envio(date.toString())
                .fecha_entrega("")
                .build();
        System.out.println("Se creó el pedido: " + envio.toString());
    }

    @When("Realizo el llamado al servicio de la Api estimar fecha entrega y le envio el id {string}")
    public void realizoElLlamadoAlServicioDeLaApiEstimarFechaEntregaYLeEnvioElId(String id_pedido) {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .body(envio)
                .post("http://localhost:8080/pedidos");

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .get("http://localhost:8080/pedidos/" + id_pedido+"/time");
    }

    @Then("Me llega el estado {int}")
    public void meLlegaElEstado(int status) {
        response.then().statusCode(status);
    }

    @And("Me llega la fecha de llegada del pedido")
    public void meLlegaLaFechaDeLlegadaDelPedido() {
        response.then()
                .body("fecha_envio",response->notNullValue());
    }
}
