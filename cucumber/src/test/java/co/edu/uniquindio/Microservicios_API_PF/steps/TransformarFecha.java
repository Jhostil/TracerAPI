package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.EnvioDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformarFecha {

    private EnvioDTO envio;
    private String nuevaFechaEntrega = "2023-04-20 16:30:00";
    private String idPedido;
    private Response response;

    @Given("que el usuario tiene un pedido con el id {string}")
    public void queElUsuarioTieneUnPedidoConElId(String id_pedido) {
        envio = EnvioDTO
                .builder()
                .id(id_pedido)
                .estado("En Reparto")
                //.fecha_envio(LocalDateTime.of(2023, 1, 16, 12, 30, 0))
                //.fecha_entrega(LocalDateTime.of(2023, 5, 12, 9, 30, 0))
                .fecha_envio("2023-03-05T10:30:00")
                .fecha_entrega("27/04/2023 15:00")
                .build();

        //Guardo el envio
        given()
                .contentType(ContentType.JSON)
                .body(envio)
                .post("http://localhost:8080/pedidos");

        this.idPedido = id_pedido;
    }

    @When("el usuario convierte la fecha de entrega a la zona horaria de {string}")
    public void elUsuarioConvierteLaFechaDeEntregaALaZonaHorariaDe(String zonaHoraria) {

        response = RestAssured.given()
                .header("ubicacion_cliente", zonaHoraria)
                .header("Authorization", "Bearer " + "authToken") // Aquí debes establecer el token de autenticación.
                .when()
                .get("http://localhost:8080/pedidos/12357/datetime_adjust"); // Aquí debes establecer el id del pedido.

        nuevaFechaEntrega = response.getBody().asString();
        System.out.println(nuevaFechaEntrega);
    }

    @Then("el usuario obtiene la nueva fecha de entrega {string}")
    public void elUsuarioObtieneLaNuevaFechaDeEntregaSegúnLaZonaHorariaDeBogota(String nuevaFecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm");
        LocalDateTime expectedDateTime = LocalDateTime.parse(nuevaFecha, formatter);
        LocalDateTime actualDateTime = LocalDateTime.parse(nuevaFechaEntrega, formatter);

        assertEquals(expectedDateTime, actualDateTime);

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .get("http://localhost:8080/pedidos/" + envio.getId() + "/datetime_adjust");
    }

    @And("una respuesta http {int}")
    public void obtengoUnCodigoDeEstado(int status) {
        response.then().statusCode(status);
    }

}
