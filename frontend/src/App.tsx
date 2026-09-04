import { useEffect, useMemo, useState } from "react";
import { fetchDashboardSummary, fetchLeads } from "./api/client";
import type { DashboardSummaryResponse, LeadResponse, LeadStatus } from "./types";
import { SummaryCards } from "./components/SummaryCards";
import { ChannelBreakdown } from "./components/ChannelBreakdown";
import { LeadsTable } from "./components/LeadsTable";

const REFRESH_INTERVAL_MS = 30_000;

export default function App() {
  const [summary, setSummary] = useState<DashboardSummaryResponse | null>(null);
  const [leads, setLeads] = useState<LeadResponse[]>([]);
  const [filtro, setFiltro] = useState<LeadStatus | "TODOS">("TODOS");
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelado = false;

    async function cargar() {
      try {
        const [nuevoSummary, nuevosLeads] = await Promise.all([
          fetchDashboardSummary(),
          fetchLeads(filtro === "TODOS" ? undefined : filtro),
        ]);
        if (!cancelado) {
          setSummary(nuevoSummary);
          setLeads(nuevosLeads);
          setError(null);
        }
      } catch (err) {
        if (!cancelado) {
          setError(err instanceof Error ? err.message : "Error desconocido al cargar el dashboard");
        }
      } finally {
        if (!cancelado) {
          setLoading(false);
        }
      }
    }

    setLoading(true);
    cargar();
    const interval = setInterval(cargar, REFRESH_INTERVAL_MS);

    return () => {
      cancelado = true;
      clearInterval(interval);
    };
  }, [filtro]);

  const subtitulo = useMemo(
    () => "Sales Copilot · Salud S.A. — actualización automática cada 30s",
    []
  );

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <h1>Dashboard Comercial</h1>
        <p>{subtitulo}</p>
      </header>

      {error && <div className="error-banner">⚠️ {error}</div>}

      {loading && !summary ? (
        <p className="empty-state">Cargando datos del backend…</p>
      ) : (
        <>
          {summary && (
            <>
              <SummaryCards summary={summary} />
              <section className="panel">
                <h2>Leads por canal</h2>
                <ChannelBreakdown leadsPorCanal={summary.leadsPorCanal} />
              </section>
            </>
          )}

          <section className="panel">
            <LeadsTable leads={leads} filtro={filtro} onFiltroChange={setFiltro} />
          </section>
        </>
      )}
    </div>
  );
}
