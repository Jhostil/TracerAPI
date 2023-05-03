package co.edu.uniquindio.Microservicios_API_PF;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

@Configuration

public class SwaggerConfig {

    //@Value("\${spring.datasource.url}")
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;


    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;
    @Bean
    public OpenAPI customOpenAPI() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream is = new ClassPathResource("JHOOIINEER_1-Tracer-1.0.0-resolved.yaml").getInputStream()) {
            return yaml.loadAs(is, OpenAPI.class);
        }
    }

}
