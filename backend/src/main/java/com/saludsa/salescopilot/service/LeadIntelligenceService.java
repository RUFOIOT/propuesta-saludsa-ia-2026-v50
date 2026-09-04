package com.saludsa.salescopilot.service;

import com.saludsa.salescopilot.domain.Lead;
import org.springframework.stereotype.Service;

/**
 * Entregable E1.1 - Lead Intelligence Agent.
 * Calcula un score IA (0-100) para priorizar el seguimiento del lead,
 * a partir de senales disponibles al momento del primer contacto.
 *
 * Nota: el modelo real (enriquecimiento via APIs publicas, LLM, etc.)
 * se conecta aqui; esta implementacion usa una heuristica base como
 * punto de partida verificable y reemplazable.
 */
@Service
public class LeadIntelligenceService {

    public int calcularScore(Lead lead, String mensaje) {
        int score = 40; // base

        if (lead.getEmail() != null && !lead.getEmail().isBlank()) {
            score += 10;
        }
        if (lead.getTelefono() != null && !lead.getTelefono().isBlank()) {
            score += 10;
        }
        if (lead.getCanalOrigen() == Lead.Channel.REFERIDO) {
            score += 20;
        }
        if (mensaje != null) {
            String texto = mensaje.toLowerCase();
            if (texto.contains("plan") || texto.contains("cotiz") || texto.contains("precio")) {
                score += 15;
            }
            if (texto.contains("urgente") || texto.contains("hoy")) {
                score += 10;
            }
        }

        return Math.max(0, Math.min(100, score));
    }

    /** Determina si el lead debe pasar automaticamente a CALIFICADO segun su score. */
    public boolean calificaAutomaticamente(int score) {
        return score >= 70;
    }
}
