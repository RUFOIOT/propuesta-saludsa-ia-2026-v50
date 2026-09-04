import type { DashboardSummaryResponse, LeadResponse, LeadStatus } from "../types";

/**
 * Base URL del backend Sales Copilot. En dev apunta a localhost:8080
 * (ver backend/README.md); en producción se define via VITE_API_BASE_URL.
 */
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080";

async function request<T>(path: string): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`);
  if (!response.ok) {
    throw new Error(`Error ${response.status} al consultar ${path}`);
  }
  return (await response.json()) as T;
}

export function fetchDashboardSummary(): Promise<DashboardSummaryResponse> {
  return request<DashboardSummaryResponse>("/api/dashboard/summary");
}

export function fetchLeads(estado?: LeadStatus): Promise<LeadResponse[]> {
  const query = estado ? `?estado=${estado}` : "";
  return request<LeadResponse[]>(`/api/leads${query}`);
}
