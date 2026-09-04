package com.saludsa.salescopilot.controller;

import com.saludsa.salescopilot.domain.Lead;
import com.saludsa.salescopilot.dto.LeadResponse;
import com.saludsa.salescopilot.repository.LeadRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Consulta de leads para el equipo de ventas / dashboard.
 */
@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadRepository leadRepository;

    public LeadController(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @GetMapping
    public List<LeadResponse> listar(@RequestParam(required = false) Lead.Status estado) {
        List<Lead> leads = (estado != null) ? leadRepository.findByEstado(estado) : leadRepository.findAll();
        return leads.stream().map(LeadResponse::from).toList();
    }

    @GetMapping("/{id}")
    public LeadResponse obtener(@PathVariable String id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lead no encontrado: " + id));
        return LeadResponse.from(lead);
    }
}
