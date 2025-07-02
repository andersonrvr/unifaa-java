package com.ecommerce.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EcommerceApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApiApplication.class, args);
        
        System.out.println("\n" +
                "========================================\n" +
                "🚀 E-COMMERCE API INICIADA COM SUCESSO!\n" +
                "========================================\n" +
                "📋 Documentação: http://localhost:8080/api/v1/swagger-ui.html\n" +
                "🔗 API Base URL: http://localhost:8080/api/v1\n" +
                "📊 Actuator: http://localhost:8080/api/v1/actuator\n" +
                "========================================\n");
    }
}

