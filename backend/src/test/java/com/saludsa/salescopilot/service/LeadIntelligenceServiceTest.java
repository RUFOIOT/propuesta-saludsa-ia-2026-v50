package com.saludsa.salescopilot.service;

import com.saludsa.salescopilot.domain.Lead;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeadIntelligenceServiceTest {

    private final LeadIntelligenceService service = new LeadIntelligenceService();

    @Test
    void leadConDatosCompletosYReferidoObtieneScoreAlto() {
        Lead lead = new Lead("Isabel Torres", "+593999999999", "isabel@example.com", Lead.Channel.REFERIDO);

        int score = service.calcularScore(lead, "Quiero cotizar un plan urgente para mi familia");

        assertTrue(score >= 70, "Se esperaba un score alto para un referido calificado, fue: " + score);
        assertTrue(service.calificaAutomaticamente(score));
    }

    @Test
    void leadSinDatosDeContactoObtieneScoreBajo() {
        Lead lead = new Lead("Anonimo", null, null, Lead.Channel.WEB_FORM);

        int score = service.calcularScore(lead, "hola");

        assertFalse(service.calificaAutomaticamente(score));
    }

    @Test
    void scoreNuncaSuperaCienNiEsNegativo() {
        Lead lead = new Lead("Test", "0999999999", "t@example.com", Lead.Channel.REFERIDO);

        int score = service.calcularScore(lead, "urgente cotizar precio plan hoy");

        assertTrue(score <= 100 && score >= 0);
    }
}
