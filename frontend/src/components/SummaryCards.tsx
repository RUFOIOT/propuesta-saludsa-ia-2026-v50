import type { DashboardSummaryResponse } from "../types";

interface Props {
  summary: DashboardSummaryResponse;
}

const CARD_DEFS: Array<{
  label: string;
  value: (s: DashboardSummaryResponse) => string;
}> = [
  { label: "Leads totales", value: (s) => s.totalLeads.toString() },
  { label: "Calificados", value: (s) => s.leadsCalificados.toString() },
  { label: "Propuestas enviadas", value: (s) => s.propuestasEnviadas.toString() },
  { label: "Cerrados (ganados)", value: (s) => s.cerradosGanados.toString() },
  { label: "Tasa de conversión", value: (s) => `${s.tasaConversion.toFixed(1)}%` },
  { label: "Score IA promedio", value: (s) => s.scorePromedio.toFixed(0) },
];

export function SummaryCards({ summary }: Props) {
  return (
    <div className="cards-grid">
      {CARD_DEFS.map((card) => (
        <div className="card" key={card.label}>
          <div className="card-value">{card.value(summary)}</div>
          <div className="card-label">{card.label}</div>
        </div>
      ))}
    </div>
  );
}
