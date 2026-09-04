package com.saludsa.salescopilot.controller;

import com.saludsa.salescopilot.domain.Lead;
import com.saludsa.salescopilot.dto.DashboardSummaryResponse;
import com.saludsa.salescopilot.repository.LeadRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Entregable E1.5 - Dashboard Comercial en tiempo real.
 * Expone los KPIs que consumiria el frontend (React) del dashboard.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final LeadRepository leadRepository;

    public DashboardController(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse resumen() {
        List<Lead> leads = leadRepository.findAll();

        long total = leads.size();
        long nuevos = contar(leads, Lead.Status.NUEVO);
        long contactados = contar(leads, Lead.Status.CONTACTADO);
        long calificados = contar(leads, Lead.Status.CALIFICADO);
        long propuestas = contar(leads, Lead.Status.PROPUESTA_ENVIADA);
        long ganados = contar(leads, Lead.Status.CERRADO_GANADO);

        double tasaConversion = total == 0 ? 0.0 : (ganados * 100.0) / total;
        double scorePromedio = total == 0 ? 0.0 : leads.stream().mapToInt(Lead::getScoreIa).average().orElse(0.0);

        Map<String, Long> porCanal = leads.stream()
                .collect(Collectors.groupingBy(l -> l.getCanalOrigen().name(), Collectors.counting()));

        return new DashboardSummaryResponse(
                total, nuevos, contactados, calificados, propuestas, ganados,
                Math.round(tasaConversion * 100.0) / 100.0,
                Math.round(scorePromedio * 100.0) / 100.0,
                porCanal
        );
    }

    private long contar(List<Lead> leads, Lead.Status estado) {
        return leads.stream().filter(l -> l.getEstado() == estado).count();
    }
}
