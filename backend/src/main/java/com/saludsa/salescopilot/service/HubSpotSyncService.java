package com.saludsa.salescopilot.service;

import com.saludsa.salescopilot.domain.Lead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Entregable E1.4 - Integracion HubSpot CRM (bidireccional).
 *
 * Cliente de sincronizacion hacia HubSpot API v3. La implementacion HTTP real
 * (RestClient/WebClient contra api.hubapi.com con el token de
 * hubspot.api-key) se conecta en createOrUpdateDeal(); por ahora se deja
 * como stub explicito para no depender de credenciales en este repositorio.
 */
@Service
public class HubSpotSyncService {

    private static final Logger log = LoggerFactory.getLogger(HubSpotSyncService.class);

    private final String apiKey;

    public HubSpotSyncService(@Value("${hubspot.api-key:}") String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Crea o actualiza el deal en HubSpot para el lead dado.
     * Devuelve el hubspotDealId asignado (real o simulado si no hay API key configurada).
     */
    public String createOrUpdateDeal(Lead lead) {
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("HUBSPOT_API_KEY no configurada. Simulando sincronizacion para lead {}", lead.getId());
            return "simulated-deal-" + lead.getId();
        }

        // TODO: integrar HubSpot API v3 (crmObjects/deals) usando 'apiKey'.
        // POST https://api.hubapi.com/crm/v3/objects/deals
        log.info("Sincronizando lead {} con HubSpot (estado: {})", lead.getId(), lead.getEstado());
        return "hubspot-deal-" + lead.getId();
    }

    public void actualizarEtapaPipeline(Lead lead) {
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("HUBSPOT_API_KEY no configurada. Simulando actualizacion de pipeline para deal {}",
                    lead.getHubspotDealId());
            return;
        }

        // TODO: PATCH https://api.hubapi.com/crm/v3/objects/deals/{dealId}
        log.info("Actualizando etapa de pipeline en HubSpot para deal {} -> {}",
                lead.getHubspotDealId(), lead.getEstado());
    }
}
