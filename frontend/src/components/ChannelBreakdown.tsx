import type { DashboardSummaryResponse } from "../types";

interface Props {
  leadsPorCanal: DashboardSummaryResponse["leadsPorCanal"];
}

const CHANNEL_LABELS: Record<string, string> = {
  WHATSAPP: "WhatsApp Business",
  TELEGRAM: "Telegram",
  WEB_FORM: "Formulario web",
  REFERIDO: "Referido",
};

export function ChannelBreakdown({ leadsPorCanal }: Props) {
  const entries = Object.entries(leadsPorCanal);
  const max = Math.max(1, ...entries.map(([, count]) => count));

  if (entries.length === 0) {
    return <p className="empty-state">Aún no hay leads registrados por canal.</p>;
  }

  return (
    <ul className="channel-list">
      {entries.map(([canal, count]) => (
        <li key={canal} className="channel-row">
          <span className="channel-name">{CHANNEL_LABELS[canal] ?? canal}</span>
          <div className="channel-bar-track">
            <div className="channel-bar-fill" style={{ width: `${(count / max) * 100}%` }} />
          </div>
          <span className="channel-count">{count}</span>
        </li>
      ))}
    </ul>
  );
}
