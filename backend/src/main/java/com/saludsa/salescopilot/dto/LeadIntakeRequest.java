package com.saludsa.salescopilot.dto;

import com.saludsa.salescopilot.domain.Lead;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Payload de entrada de un webhook de WhatsApp/Telegram cuando llega
 * un nuevo mensaje/lead (entregable E1.2 - Agente Conversion 24/7).
 */
public record LeadIntakeRequest(
        @NotBlank String nombre,
        String telefono,
        String email,
        @NotNull Lead.Channel canal,
        @NotBlank String mensaje
) {
}
