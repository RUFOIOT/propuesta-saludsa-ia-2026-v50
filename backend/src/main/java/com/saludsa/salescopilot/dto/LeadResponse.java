package com.saludsa.salescopilot.dto;

import com.saludsa.salescopilot.domain.Lead;
import java.time.Instant;

/**
 * Representacion de salida de un Lead expuesta por la API
 * (usada por LeadController y el futuro dashboard React).
 */
public record LeadResponse(
        String id,
        String nombre,
        String telefono,
        String email,
        Lead.Channel canalOrigen,
        Lead.Status estado,
        int scoreIa,
        String hubspotDealId,
        Instant creadoEn,
        Instant actualizadoEn
) {
    public static LeadResponse from(Lead lead) {
        return new LeadResponse(
                lead.getId(),
                lead.getNombre(),
                lead.getTelefono(),
                lead.getEmail(),
                lead.getCanalOrigen(),
                lead.getEstado(),
                lead.getScoreIa(),
                lead.getHubspotDealId(),
                lead.getCreadoEn(),
                lead.getActualizadoEn()
        );
    }
}
