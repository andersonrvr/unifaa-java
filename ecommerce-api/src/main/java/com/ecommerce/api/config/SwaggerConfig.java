package com.ecommerce.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce API")
                        .version("1.0.0")
                        .description("API completa para sistema de E-Commerce com funcionalidades de:\n" +
                                   "• CRUD de Produtos\n" +
                                   "• CRUD de Categorias\n" +
                                   "• CRUD de Clientes\n" +
                                   "• CRUD de Endereços\n" +
                                   "• Consultas personalizadas")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("dev@ecommerce.com")
                                .url("https://github.com/ecommerce-api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/api/v1")
                                .description("Servidor de Desenvolvimento")
                ));
    }
}