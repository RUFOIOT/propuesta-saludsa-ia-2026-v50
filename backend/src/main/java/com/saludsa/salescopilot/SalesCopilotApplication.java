package com.saludsa.salescopilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point del backend Sales Copilot (Sales Pilot v2).
 * Expone los endpoints de intake de leads (WhatsApp/Telegram),
 * comandos del bot, y el dashboard comercial descritos en la
 * propuesta tecnico-comercial (index.html) de este repositorio.
 */
@SpringBootApplication
public class SalesCopilotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesCopilotApplication.class, args);
    }
}
