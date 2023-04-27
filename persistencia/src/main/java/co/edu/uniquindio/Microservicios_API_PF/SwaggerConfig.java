package co.edu.uniquindio.Microservicios_API_PF;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

@Configuration

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream is = new ClassPathResource("JHOOIINEER_1-Tracer-1.0.0-resolved.yaml").getInputStream()) {
            return yaml.loadAs(is, OpenAPI.class);
        }
    }

}
