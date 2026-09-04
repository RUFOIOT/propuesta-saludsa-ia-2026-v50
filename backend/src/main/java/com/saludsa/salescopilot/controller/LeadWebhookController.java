package com.saludsa.salescopilot.controller;

import com.saludsa.salescopilot.domain.Lead;
import com.saludsa.salescopilot.dto.LeadIntakeRequest;
import com.saludsa.salescopilot.dto.LeadResponse;
import com.saludsa.salescopilot.service.ConversationAgentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Webhook de entrada para WhatsApp Business y Telegram (E1.2).
 * En produccion, el proveedor de mensajeria (Meta/Telegram) invoca este
 * endpoint por cada mensaje entrante; aqui se normaliza a LeadIntakeRequest.
 */
@RestController
@RequestMapping("/api/webhooks")
public class LeadWebhookController {

    private final ConversationAgentService conversationAgentService;

    public LeadWebhookController(ConversationAgentService conversationAgentService) {
        this.conversationAgentService = conversationAgentService;
    }

    @PostMapping("/leads")
    public ResponseEntity<LeadResponse> recibirMensaje(@Valid @RequestBody LeadIntakeRequest request) {
        Lead lead = conversationAgentService.procesarMensajeEntrante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(LeadResponse.from(lead));
    }
}
