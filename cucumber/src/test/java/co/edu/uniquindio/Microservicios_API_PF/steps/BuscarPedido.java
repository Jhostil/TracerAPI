package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.EnvioDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.UsuarioDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class BuscarPedido {

    private UsuarioDTO usuario;

    private EnvioDTO envio;

    private Response response;


    @Given("Soy un usuario que estoy autenticado en el sistema")
    public void soyUnUsuarioQueEstoyAutenticadoEnElSistema() {
        usuario = UsuarioDTO
                .builder()
                .usuario("pedro")
                .clave("pedro")
                .build();
    }

    @And("existe un pedido con el identificador {string}")
    public void existeUnPedidoConElIdentificador(String id_pedido) {
        envio = EnvioDTO
                .builder()
                .id(id_pedido)
                .fecha_envio("10/8/2022/05:00")
                .fecha_entrega("")
                .build();
        System.out.println("Se creó el pedido: " + envio.toString());
    }

    @When("invoco el servicio de busqueda de envios ingresando el id {string}")
    public void invocoElServicioDeBusquedaDeEnviosIngresandoElId(String id_pedido) {
        //Guardo el envio
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .body(envio)
                .post("http://localhost:8080/pedidos");

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .get("http://localhost:8080/pedidos/" + id_pedido);

    }

    @Then("obtengo un status code {int}")
    public void obtengoUnStatusCode(int status) {
        response.then().statusCode(status);
    }

    @And("la información del envio")
    public void laInformacionDelEnvio() {
        response.then()
                .body("id",response->notNullValue())
                .body("estado",response->notNullValue())
                .body("fecha_envio",response->notNullValue())
                .body("fecha_entrega",response->notNullValue());
    }
}
