package com.saludsa.salescopilot.service;

import com.saludsa.salescopilot.domain.Interaction;
import com.saludsa.salescopilot.domain.Lead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Entregable E1.7 - Google Sheets Trazabilidad (Backup BI).
 *
 * Alimenta el Google Sheets maestro descrito en la propuesta con 5 tabs:
 * Leads, Interacciones, Performance Agentes, Funnel de Conversion,
 * KPIs Gerenciales. La escritura real via Google Sheets API se conecta
 * en los metodos appendX(); por ahora quedan como stubs con logging.
 */
@Service
public class GoogleSheetsTraceabilityService {

    private static final Logger log = LoggerFactory.getLogger(GoogleSheetsTraceabilityService.class);

    public void appendLeadRow(Lead lead) {
        // TODO: Google Sheets API v4 - spreadsheets.values.append sobre tab "Leads"
        log.info("[Sheets:Leads] {} | canal={} | score={} | estado={}",
                lead.getId(), lead.getCanalOrigen(), lead.getScoreIa(), lead.getEstado());
    }

    public void appendInteractionRow(Interaction interaction) {
        // TODO: Google Sheets API v4 - tab "Interacciones"
        log.info("[Sheets:Interacciones] lead={} | canal={} | direccion={} | comando={}",
                interaction.getLeadId(), interaction.getCanal(), interaction.getDireccion(),
                interaction.getComandoBot());
    }

    public void appendFunnelTransition(Lead lead, Lead.Status estadoAnterior) {
        // TODO: Google Sheets API v4 - tab "Funnel de Conversion"
        log.info("[Sheets:Funnel] lead={} | {} -> {}", lead.getId(), estadoAnterior, lead.getEstado());
    }
}
