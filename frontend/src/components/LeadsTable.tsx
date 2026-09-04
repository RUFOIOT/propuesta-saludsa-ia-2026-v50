import type { LeadResponse, LeadStatus } from "../types";

interface Props {
  leads: LeadResponse[];
  filtro: LeadStatus | "TODOS";
  onFiltroChange: (filtro: LeadStatus | "TODOS") => void;
}

const ESTADOS: Array<LeadStatus | "TODOS"> = [
  "TODOS",
  "NUEVO",
  "CONTACTADO",
  "CALIFICADO",
  "PROPUESTA_ENVIADA",
  "EN_NEGOCIACION",
  "CERRADO_GANADO",
  "CERRADO_PERDIDO",
];

function formatFecha(iso: string): string {
  try {
    return new Date(iso).toLocaleString("es-EC", { dateStyle: "short", timeStyle: "short" });
  } catch {
    return iso;
  }
}

export function LeadsTable({ leads, filtro, onFiltroChange }: Props) {
  return (
    <div className="leads-table-wrap">
      <div className="leads-table-header">
        <h2>Leads activos</h2>
        <select
          value={filtro}
          onChange={(e) => onFiltroChange(e.target.value as LeadStatus | "TODOS")}
          aria-label="Filtrar leads por estado"
        >
          {ESTADOS.map((estado) => (
            <option key={estado} value={estado}>
              {estado}
            </option>
          ))}
        </select>
      </div>

      {leads.length === 0 ? (
        <p className="empty-state">No hay leads para el filtro seleccionado.</p>
      ) : (
        <table className="leads-table">
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Canal</th>
              <th>Estado</th>
              <th>Score IA</th>
              <th>Actualizado</th>
            </tr>
          </thead>
          <tbody>
            {leads.map((lead) => (
              <tr key={lead.id}>
                <td>{lead.nombre}</td>
                <td>{lead.canalOrigen}</td>
                <td>
                  <span className={`status-badge status-${lead.estado.toLowerCase()}`}>{lead.estado}</span>
                </td>
                <td>{lead.scoreIa}</td>
                <td>{formatFecha(lead.actualizadoEn)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
