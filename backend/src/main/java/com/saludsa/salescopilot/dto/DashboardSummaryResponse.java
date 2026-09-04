package com.saludsa.salescopilot.dto;

import java.util.Map;

/**
 * Resumen de KPIs para el Dashboard Comercial (entregable E1.5),
 * equivalente al tab "KPIs Gerenciales" de Google Sheets.
 */
public record DashboardSummaryResponse(
        long totalLeads,
        long leadsNuevos,
        long leadsContactados,
        long leadsCalificados,
        long propuestasEnviadas,
        long cerradosGanados,
        double tasaConversion,
        double scorePromedio,
        Map<String, Long> leadsPorCanal
) {
}
