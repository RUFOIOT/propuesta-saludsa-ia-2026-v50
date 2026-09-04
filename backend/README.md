# Sales Copilot — Backend (Java / Spring Boot)

Backend del agente **Sales Copilot (Sales Pilot v2)** para Salud S.A., implementando los entregables descritos en la propuesta técnico-comercial (`../index.html`).

## Stack

- Java 17 · Spring Boot 3.3 (Web, Data JPA, Validation)
- H2 en memoria para desarrollo local · PostgreSQL como driver de producción
- JUnit 5

## Estructura

```
backend/
  src/main/java/com/saludsa/salescopilot/
    domain/       Lead, Interaction (entidades JPA)
    dto/          Requests/Responses de la API
    repository/   Spring Data JPA repositories
    service/      Lead Intelligence, HubSpot sync, Google Sheets, orquestación conversacional
    controller/   Webhooks (WhatsApp/Telegram), comandos de bot, leads, dashboard
    exception/    Manejo global de errores
  src/main/resources/application.yml
  src/test/java/  Tests unitarios
```

## Endpoints principales

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/webhooks/leads` | Recibe un mensaje entrante (WhatsApp/Telegram) y crea/actualiza el lead |
| `POST` | `/api/leads/{leadId}/commands/{comando}` | Ejecuta un comando de bot: `propuesta`, `cotizar`, `seguimiento`, `agenda`, `briefing` |
| `GET` | `/api/leads` | Lista leads (filtro opcional `?estado=`) |
| `GET` | `/api/leads/{id}` | Detalle de un lead |
| `GET` | `/api/dashboard/summary` | KPIs para el Dashboard Comercial |

## Ejecutar en local

```bash
cd backend
./mvnw spring-boot:run   # o: mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`, con base H2 en memoria (consola en `/h2-console`).

## Variables de entorno

| Variable | Uso |
|---|---|
| `HUBSPOT_API_KEY` | Token de HubSpot API v3 (integración E1.4). Sin configurar, `HubSpotSyncService` simula la sincronización. |
| `GOOGLE_SHEETS_SPREADSHEET_ID` | Spreadsheet destino de la trazabilidad (E1.7). |
| `DB_URL` / `DB_USER` / `DB_PASSWORD` | Conexión a PostgreSQL en producción (por defecto usa H2 en memoria). |

## Pendiente de integración real

Los siguientes puntos están dejados como stubs explícitos (`TODO` en el código), listos para conectar cuando existan credenciales:

- `HubSpotSyncService` — llamadas HTTP reales a `api.hubapi.com`
- `GoogleSheetsTraceabilityService` — escritura real vía Google Sheets API v4
- Orquestación NVIDIA NEMO / n8n / LangGraph (E1.9) — `ConversationAgentService` expone el contrato que el orquestador agéntico invocaría
