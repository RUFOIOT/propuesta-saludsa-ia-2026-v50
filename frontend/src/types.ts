/**
 * Tipos alineados con los DTOs del backend Java
 * (com.saludsa.salescopilot.dto.*, com.saludsa.salescopilot.domain.Lead).
 */

export type LeadChannel = "WHATSAPP" | "TELEGRAM" | "WEB_FORM" | "REFERIDO";

export type LeadStatus =
  | "NUEVO"
  | "CONTACTADO"
  | "CALIFICADO"
  | "PROPUESTA_ENVIADA"
  | "EN_NEGOCIACION"
  | "CERRADO_GANADO"
  | "CERRADO_PERDIDO";

export interface LeadResponse {
  id: string;
  nombre: string;
  telefono: string | null;
  email: string | null;
  canalOrigen: LeadChannel;
  estado: LeadStatus;
  scoreIa: number;
  hubspotDealId: string | null;
  creadoEn: string;
  actualizadoEn: string;
}

export interface DashboardSummaryResponse {
  totalLeads: number;
  leadsNuevos: number;
  leadsContactados: number;
  leadsCalificados: number;
  propuestasEnviadas: number;
  cerradosGanados: number;
  tasaConversion: number;
  scorePromedio: number;
  leadsPorCanal: Record<string, number>;
}
