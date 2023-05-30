package co.edu.uniquindio.Microservicios_API_PF;

import co.edu.uniquindio.Microservicios_API_PF.entidades.Usuario;
import co.edu.uniquindio.Microservicios_API_PF.servicios.TokenServicio;
import co.edu.uniquindio.Microservicios_API_PF.servicios.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/tokens")
public class TokenController {

    private final TokenServicio tokenService;
    private final UsuarioServicio usuarioServicio;

    public TokenController(TokenServicio tokenService, UsuarioServicio usuarioServicio) {
        this.tokenService = tokenService;
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/generacion")
    public ResponseEntity<String> generateToken(@RequestParam String subject) {
        String tokenString = tokenService.generateToken(subject);
        return ResponseEntity.ok(tokenString);
    }

    @GetMapping("/validacion")
    public ResponseEntity<Boolean> validateToken(@RequestParam String tokenString, @RequestParam String correo) {
        Optional<Usuario> usuario = usuarioServicio.obtenerUsuarioPorCorreo(correo);
        if(usuario.isPresent()){
            boolean isValid = tokenService.validateToken(tokenString, usuario.get());
            System.out.println("Entre");
            return ResponseEntity.ok(isValid);
        }else
            throw new RuntimeException("El usuario buscado no ha sido encontrado");
    }

    @GetMapping("/sujeto")
    public ResponseEntity<String> getSubjectFromToken(@RequestParam String tokenString) {
        String subject = tokenService.getSubjectFromToken(tokenString);
        return ResponseEntity.ok(subject);
    }

}
