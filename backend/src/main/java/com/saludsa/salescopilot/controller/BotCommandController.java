package com.saludsa.salescopilot.controller;

import com.saludsa.salescopilot.domain.Lead;
import com.saludsa.salescopilot.dto.LeadResponse;
import com.saludsa.salescopilot.service.ConversationAgentService;
import org.springframework.web.bind.annotation.*;

/**
 * Comandos operables por asesores en WhatsApp/Telegram (E1.6):
 * propuesta / cotizar / seguimiento / agenda / briefing.
 */
@RestController
@RequestMapping("/api/leads/{leadId}/commands")
public class BotCommandController {

    private final ConversationAgentService conversationAgentService;

    public BotCommandController(ConversationAgentService conversationAgentService) {
        this.conversationAgentService = conversationAgentService;
    }

    @PostMapping("/{comando}")
    public LeadResponse ejecutarComando(@PathVariable String leadId, @PathVariable String comando) {
        Lead lead = conversationAgentService.ejecutarComando(leadId, comando);
        return LeadResponse.from(lead);
    }
}
