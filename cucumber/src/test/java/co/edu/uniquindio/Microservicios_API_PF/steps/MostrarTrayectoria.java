package co.edu.uniquindio.Microservicios_API_PF.steps;

import co.edu.uniquindio.Microservicios_API_PF.dto.EnvioDTO;
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

public class MostrarTrayectoria {

    private UsuarioDTO usuario;

    private EnvioDTO envio;

    private UbicacionDTO ubicacion;

    private Response response;


    @Given("Soy un usuario que estoy con sesion activa en el sistema")
    public void soyUnUsuarioQueEstoyConSesionActivaEnElSistema() {
        usuario = UsuarioDTO
                .builder()
                .usuario("pedro")
                .clave("pedro")
                .build();
    }

    @And("hay un pedido con el identificador {string}")
    public void hayUnPedidoConElIdentificador(String id_pedido) {
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

        envio = EnvioDTO
                .builder()
                .id(id_pedido).fecha_envio("10/8/2022/05:00")
                .fecha_entrega("")
                .ubicaciones(ubicaciones)
                .build();
    }

    @When("invoco el servicio que permite ver la trayectoria de un pedido ingresando el id {string}")
    public void invocoElServicioQuePermiteVerLaTrayectoriaDeUnPedidoIngresandoElId(String id_pedido) {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .body(envio)
                .post("http://localhost:8080/pedidos");

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer miToken")
                .get("http://localhost:8080/pedidos/" + id_pedido + "/tracer");
    }

    @Then("obtengo un codigo de estado {int}")
    public void obtengoUnCodigoDeEstado(int status) {
        response.then().statusCode(status);
    }

    @And("un arreglo con las ubicaciones del pedido")
    public void unArregloConLasUbicacionesDelPedido() {
        response.then()
                .body("id",response->notNullValue())
                .body("ubicaciones",response->notNullValue());
    }
}
