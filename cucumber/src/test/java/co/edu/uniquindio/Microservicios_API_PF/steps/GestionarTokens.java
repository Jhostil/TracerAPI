package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.EnvioDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GestionarTokens {

    private String username;
    private String password;
    private String token;
    private String subject="Maria";

    private Response response;

    @Given("que tengo credenciales de usuario válidas")
    public void que_tengo_credenciales_de_usuario_validas() {
        username = "usuario";
        password = "contraseña";
    }

    @When("envío una solicitud POST al endpoint de generacion con las credenciales de usuario")
    public void envio_una_solicitud_POST_al_endpoint_generacion_con_las_credenciales_de_usuario() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParam("subject", subject)
                .body("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }")
                .post("http://localhost:8080/tokens/generacion");

        token = response.getBody().asString();
        System.out.println(token);
    }

    @Then("la respuesta debe contener un token de autenticación válido")
    public void la_respuesta_debe_contener_un_token_de_autenticacion_valido() {
        assertTrue(token.matches("^\\S+\\.\\S+\\.\\S+$"));
    }

    @Given("que tengo un token de autenticación válido")
    public void que_tengo_un_token_de_autenticacion_valido() {
        EnvioDTO envio = EnvioDTO
                .builder()
                .id("")
                .estado("En Reparto")
                .fecha_envio("2023-03-05T10:30:00")
                .fecha_entrega("2023-04-14T09:00:00")
                .build();

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParam("subject", subject)
                .body(envio)
                .post("http://localhost:8080/tokens/generacion");

        token = response.getBody().asString(); // agregar esta línea para almacenar el nuevo token

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParam("tokenString", token)
                .get("http://localhost:8080/tokens/validacion");
    }

    @When("envío una solicitud GET al endpoint de validacion con el token de autenticación")
    public void envio_una_solicitud_GET_al_endpoint_validacion_con_el_token_de_autenticacion() {
        assertEquals("true", response.getBody().asString());
    }

    @Then("la respuesta debe indicar que el token de autenticación es válido")
    public void la_respuesta_debe_indicar_que_el_token_de_autenticacion_es_valido() {
        assertTrue(response.getBody().asString().equals("true"));
    }

    @When("envío una solicitud GET al endpoint sujeto con el token de autenticación")
    public void envio_una_solicitud_GET_al_endpoint_sujeto_con_el_token_de_autenticacion() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParam("tokenString", token)
                .get("http://localhost:8080/tokens/sujeto");
    }

    @Then("la respuesta debe contener el sujeto del token de autenticación")
    public void la_respuesta_debe_contener_el_sujeto_del_token_de_autenticacion() {
        assertTrue(response.getBody().asString().equals(subject));
    }
}