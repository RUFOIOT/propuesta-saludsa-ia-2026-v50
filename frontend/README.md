# Sales Copilot — Dashboard Comercial (React)

Frontend del **Dashboard Comercial** (entregable E1.5 de la propuesta) del agente Sales Copilot, consumiendo la API del backend Java (`../backend`).

## Stack

- React 18 + TypeScript
- Vite

## Ejecutar en local

1. Levanta primero el backend (`../backend`, puerto `8080` por defecto).
2. Instala dependencias y arranca el dev server:

```bash
cd frontend
cp .env.example .env   # ajusta VITE_API_BASE_URL si es necesario
npm install
npm run dev
```

El dashboard queda disponible en `http://localhost:5173`.

## Build de producción

```bash
npm run build
npm run preview
```

## Estructura

```
frontend/
  src/
    api/client.ts         Cliente fetch hacia /api/dashboard y /api/leads
    components/
      SummaryCards.tsx     Tarjetas de KPIs (leads, conversión, score IA)
      ChannelBreakdown.tsx Distribución de leads por canal
      LeadsTable.tsx       Tabla de leads con filtro por estado
    types.ts               Tipos alineados con los DTOs del backend
    App.tsx                Composición del dashboard, polling cada 30s
    main.tsx / styles.css  Entry point y estilos
```

## Notas

- El dashboard hace polling cada 30 segundos contra el backend (`GET /api/dashboard/summary`, `GET /api/leads`). Para tiempo real más estricto, se puede migrar a WebSocket/SSE sobre el mismo backend.
- Los estilos reutilizan la paleta navy/gold de la propuesta técnico-comercial (`../index.html`) para mantener identidad visual entre el documento comercial y el producto.
