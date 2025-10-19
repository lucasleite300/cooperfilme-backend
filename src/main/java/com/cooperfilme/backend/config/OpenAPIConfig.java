package com.cooperfilme.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API do Sistema de Roteiros")
                        .version("v1.0")
                        .description("Documentação da API do projeto Spring Boot")
                        .contact(new Contact()
                                .name("Letícia Reis")
                                .email("contato@seudominio.com")));
    }
}

