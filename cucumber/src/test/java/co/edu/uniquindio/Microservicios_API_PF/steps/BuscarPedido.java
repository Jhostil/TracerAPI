package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.EnvioDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.UsuarioDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;

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
                .fecha_envio("2023-03-05T10:30:00")
                .fecha_entrega("2023-04-14T09:00:00")
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
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000)
                .build();

        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .build();

        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig().httpClient(httpClient));

        response.then()
                .body("id",response->notNullValue())
//                .body("estado",response->notNullValue())
                .body("fecha_envio",response->notNullValue())
                .body("fecha_entrega",response->notNullValue());
    }
}
