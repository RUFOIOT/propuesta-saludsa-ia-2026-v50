package com.saludsa.salescopilot.service;

import com.saludsa.salescopilot.domain.Interaction;
import com.saludsa.salescopilot.domain.Lead;
import com.saludsa.salescopilot.dto.LeadIntakeRequest;
import com.saludsa.salescopilot.repository.InteractionRepository;
import com.saludsa.salescopilot.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orquesta el flujo de conversion 24/7 (E1.2): recibe el mensaje entrante,
 * calcula score IA, persiste el lead y la interaccion, sincroniza con
 * HubSpot y registra trazabilidad en Google Sheets.
 *
 * Punto de integracion natural con el motor NVIDIA NEMO / n8n / LangGraph
 * descrito en la propuesta (E1.9): este servicio expone el "contrato" que
 * el orquestador agentico invocaria para cada mensaje entrante.
 */
@Service
public class ConversationAgentService {

    private final LeadRepository leadRepository;
    private final InteractionRepository interactionRepository;
    private final LeadIntelligenceService leadIntelligenceService;
    private final HubSpotSyncService hubSpotSyncService;
    private final GoogleSheetsTraceabilityService sheetsService;

    public ConversationAgentService(LeadRepository leadRepository,
                                     InteractionRepository interactionRepository,
                                     LeadIntelligenceService leadIntelligenceService,
                                     HubSpotSyncService hubSpotSyncService,
                                     GoogleSheetsTraceabilityService sheetsService) {
        this.leadRepository = leadRepository;
        this.interactionRepository = interactionRepository;
        this.leadIntelligenceService = leadIntelligenceService;
        this.hubSpotSyncService = hubSpotSyncService;
        this.sheetsService = sheetsService;
    }

    @Transactional
    public Lead procesarMensajeEntrante(LeadIntakeRequest request) {
        Lead lead = new Lead(request.nombre(), request.telefono(), request.email(), request.canal());

        int score = leadIntelligenceService.calcularScore(lead, request.mensaje());
        lead.asignarScore(score);
        lead.avanzarEstado(Lead.Status.CONTACTADO);

        if (leadIntelligenceService.calificaAutomaticamente(score)) {
            lead.avanzarEstado(Lead.Status.CALIFICADO);
        }

        lead = leadRepository.save(lead); // genera lead.getId() antes de sincronizar con HubSpot

        String dealId = hubSpotSyncService.createOrUpdateDeal(lead);
        lead.vincularHubspot(dealId);
        lead = leadRepository.save(lead);

        Interaction interaction = new Interaction(
                lead.getId(), lead.getCanalOrigen(), Interaction.Direction.ENTRANTE, null, request.mensaje());
        interactionRepository.save(interaction);

        sheetsService.appendLeadRow(lead);
        sheetsService.appendInteractionRow(interaction);

        return lead;
    }

    @Transactional
    public Lead ejecutarComando(String leadId, String comando) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new IllegalArgumentException("Lead no encontrado: " + leadId));

        Lead.Status estadoAnterior = lead.getEstado();

        switch (comando) {
            case "propuesta" -> lead.avanzarEstado(Lead.Status.PROPUESTA_ENVIADA);
            case "seguimiento" -> lead.avanzarEstado(Lead.Status.EN_NEGOCIACION);
            case "cotizar", "agenda", "briefing" -> {
                // Comandos informativos: no cambian el estado del lead por si mismos.
            }
            default -> throw new IllegalArgumentException("Comando de bot no soportado: " + comando);
        }

        hubSpotSyncService.actualizarEtapaPipeline(lead);
        lead = leadRepository.save(lead);

        Interaction interaction = new Interaction(
                lead.getId(), lead.getCanalOrigen(), Interaction.Direction.SALIENTE, comando, null);
        interactionRepository.save(interaction);

        sheetsService.appendInteractionRow(interaction);
        if (estadoAnterior != lead.getEstado()) {
            sheetsService.appendFunnelTransition(lead, estadoAnterior);
        }

        return lead;
    }
}
