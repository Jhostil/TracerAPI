package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.EnvioDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.TransportadoraDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.UbicacionDTO;
import co.edu.uniquindio.Microservicios_API_PF.dto.UsuarioDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class MostrarInfoTransportadora {

    private UsuarioDTO usuario;

    private TransportadoraDTO transportadora;

    private EnvioDTO envio;

    private UbicacionDTO ubicacion;

    private Response response;

    @Given("Soy un usuario  autenticado en el sistema")
    public void soyUnUsuarioAutenticadoEnElSistema() {
        usuario = UsuarioDTO
                .builder()
                .usuario("pedro")
                .clave("pedro")
                .build();
    }

    @And("existe un pedido con el id {string}")
    public void existeUnPedidoConElId(String id_pedido) {

        List<UbicacionDTO> ubicaciones = new ArrayList<>();

        ubicacion = UbicacionDTO
                .builder()
                .latitud(-23.098738)
                .longitud(12.462098)
                .build();

        ubicaciones.add(ubicacion);

        //ubicacion = new UbicacionDTO(-70.109284,-15.633680);
        ubicacion = UbicacionDTO
                .builder()
                .latitud(-70.109284)
                .longitud(-15.633680)
                .build();
        ubicaciones.add(ubicacion);

        transportadora = TransportadoraDTO
                .builder()
                .nombre("Servientrega")
                .direccion("cra 19 # 19-21 Armenia")
                .telefono("7584752")
                .build();

        envio = EnvioDTO
                .builder()
                .id(id_pedido)
                .fecha_envio("10/8/2022/05:00")
                .fecha_entrega("")
                .transportadora(transportadora)
                .ubicaciones(ubicaciones)
                .build();
    }

    @When("invoco el servicio de busqueda de la empresa transportadora ingresando el id {string}")
    public void invocoElServicioDeBusquedaDeLaEmpresaTransportadoraIngresandoElId(String id_pedido) {
        //Guardo el envio
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .body(envio)
                .post("http://localhost:8080/pedidos");

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .get("http://localhost:8080/pedidos/" + id_pedido + "/transportadoras");
    }

    @Then("obtengo un codigo http {int}")
    public void obtengoUnCodigoHttp(int status) {
        response.then().statusCode(status);
    }

    @And("la información de la transportadora")
    public void laInformacionDeLaTransportadora() {
        response.then()
                .body("id",response->notNullValue())
                .body("transportadora",response->notNullValue());
    }
}
