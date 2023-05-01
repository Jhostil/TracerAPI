package co.edu.uniquindio.Microservicios_API_PF;

import co.edu.uniquindio.Microservicios_API_PF.servicios.TokenServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tokens")
public class TokenController {

    private final TokenServicio tokenService;

    public TokenController(TokenServicio tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/generacion")
    public ResponseEntity<String> generateToken(@RequestParam String subject) {
        String tokenString = tokenService.generateToken(subject);
        return ResponseEntity.ok(tokenString);
    }

    @GetMapping("/validacion")
    public ResponseEntity<Boolean> validateToken(@RequestParam String tokenString) {
        boolean isValid = tokenService.validateToken(tokenString);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/sujeto")
    public ResponseEntity<String> getSubjectFromToken(@RequestParam String tokenString) {
        String subject = tokenService.getSubjectFromToken(tokenString);
        return ResponseEntity.ok(subject);
    }

}
