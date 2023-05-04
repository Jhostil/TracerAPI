package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.DescripcionDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.EnvioDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.EstadoDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformarFecha {

    private EnvioDTO envio;
    private String nuevaFechaEntrega = "2023-04-20 16:30:00";
    private Response response;

    @Given("que el usuario tiene un pedido con el id {string}")
    public void queElUsuarioTieneUnPedidoConElId(String id_pedido) {
        List<EstadoDTO> estados = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();
        estados.add(new EstadoDTO(id_pedido,"llego a New York", DescripcionDTO.EN_BODEGA));
        envio = EnvioDTO
                .builder()
                .id(id_pedido)
                .estado(estados)
                .fecha_envio("05/03/2023/10:30")
                .fecha_entrega("27/04/2023/16:00")
                .build();
        //Guardo el envio
        given()
                .contentType(ContentType.JSON)
                .body(envio)
                .post("http://localhost:8080/pedidos");

    }

    @When("el usuario convierte la fecha de entrega a la zona horaria de {string}")
    public void elUsuarioConvierteLaFechaDeEntregaALaZonaHorariaDe(String zonaHoraria) {

        response = RestAssured.given()
                .queryParam("zona_horaria", zonaHoraria)
                .when()
                .get("http://localhost:8080/pedidos/" + envio.getId() +"/datetime_adjust"); // Aquí debes establecer el id del pedido.

        nuevaFechaEntrega = response.getBody().asString();
        System.out.println(nuevaFechaEntrega);
    }

    @Then("el usuario obtiene la nueva fecha de entrega {string}")
    public void elUsuarioObtieneLaNuevaFechaDeEntregaSegúnLaZonaHorariaDeBogota(String nuevaFecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy/HH:mm");
        LocalDateTime expectedDateTime = LocalDateTime.parse(nuevaFecha, formatter);
        LocalDateTime actualDateTime = LocalDateTime.parse(nuevaFechaEntrega, formatter);

        assertEquals(expectedDateTime, actualDateTime);

        response = given()
                .contentType(ContentType.JSON)
                .queryParam("zona_horaria", "America/Bogota")
                .get("http://localhost:8080/pedidos/" + envio.getId() + "/datetime_adjust");
    }

    @And("una respuesta http {int}")
    public void obtengoUnCodigoDeEstado(int status) {
        response.then().statusCode(status);
    }

}
