# Sales Copilot · Salud S.A. × IA 2026 · v5.0

Propuesta técnico-comercial para la **transformación agéntica con IA** del ciclo de ventas de Salud S.A., sobre arquitectura **Microsoft Azure Enterprise** certificada con **NVIDIA NEMO — Enterprise Agentic Orchestration Platform**.

## 👥 Equipo del proyecto

| Rol | GitHub | Estado |
|---|---|---|
| Dev Senior | [@RUFOIOT](https://github.com/RUFOIOT) | Owner |
| Ingeniero líder / Builder & Developer | [@Pipo0516](https://github.com/Pipo0516) (Nicolás González) | ✅ Colaborador activo |
| Developer | [@jeddiewars](https://github.com/jeddiewars) (Jonathan Guerrero) | Invitación pendiente |
| Developer | [@ISRAELX25](https://github.com/ISRAELX25) (Israel Orellana) | Invitación pendiente |

## 📄 Contenido del repositorio

| Archivo | Descripción |
|---|---|
| `index.html` | Propuesta técnico-comercial completa de Sales Copilot: resumen ejecutivo, entregables, arquitectura, cronograma, inversión y términos. |
| `isabel-journey.yaml` | Caso de prueba (QA) del customer journey de un lead ficticio ("Isabel") a través del funnel completo del agente: Lead → Contactado → Calificado → Propuesta → Cierre, con trazabilidad en HubSpot y Google Sheets. |
| `backend/` | Backend Java (Spring Boot) del agente: Lead Intelligence, webhooks WhatsApp/Telegram, integración HubSpot, trazabilidad y API del dashboard. Ver [`backend/README.md`](backend/README.md). |

## 💻 Código fuente — Backend (Java / Spring Boot)

Implementación inicial de los entregables técnicos de Sales Copilot, en `backend/`:

- **Lead Intelligence** (`LeadIntelligenceService`) — scoring 0-100 de cada lead
- **Conversión 24/7** (`LeadWebhookController`, `ConversationAgentService`) — webhook de WhatsApp/Telegram
- **Comandos de bot** (`BotCommandController`) — `propuesta` / `cotizar` / `seguimiento` / `agenda` / `briefing`
- **Integración HubSpot CRM** (`HubSpotSyncService`) — sincronización bidireccional (stub listo para credenciales reales)
- **Trazabilidad Google Sheets** (`GoogleSheetsTraceabilityService`) — stub listo para Sheets API v4
- **Dashboard Comercial** (`DashboardController`) — KPIs en tiempo real vía API REST
- Tests unitarios con JUnit 5

Ver [`backend/README.md`](backend/README.md) para instrucciones de ejecución local (`mvn spring-boot:run`) y variables de entorno.

## 🚀 Resumen ejecutivo

Transformación del ciclo comercial de Salud S.A. mediante un agente de ventas agéntico con automatización, personalización y respuesta 24/7.

| Concepto | Valor |
|---|---|
| Inversión neta (sin IVA) | $25,000 |
| Total con IVA (15%) | $28,750 |
| Implementación | 12 semanas |
| Mantenimiento mensual (c/IVA, desde Mes 4) | $992.45 |

## 🤖 Sales Copilot — Sales Pilot v2

Agente multifunción de ventas: lead intelligence, conversión 24/7 e hiperpersonalización, con HubSpot CRM bidireccional, Google Sheets de trazabilidad, WhatsApp Business y NVIDIA NEMO.

- Lead & Assistant Intelligence
- Agente de conversión 24/7 (WhatsApp + Telegram)
- Motor de hiperpersonalización — landing única por prospecto
- Integración bidireccional con HubSpot CRM
- Google Sheets — backup de trazabilidad (5 tabs)
- Dashboard comercial en tiempo real
- Capacitación al equipo de ventas

**Inversión:** $25,000 + IVA 15% = $28,750 · **12 semanas**

## ⚙️ Arquitectura y seguridad

Azure WAF · Azure Entra ID MFA · Azure RBAC + PIM · NVIDIA NEMO Enterprise · Zero PII in Transit

Cumplimiento: LOPDP Ecuador · SBS · ISO 27001

## ☁️ Modelo de entrega

El desarrollo se implementa en la nube de Azure bajo los lineamientos, estándares y entregables de seguridad definidos por Salud S.A. Se entrega el código fuente y todo lo necesario para que Salud S.A. pueda operar y mantener la solución de forma autónoma. El mantenimiento técnico del proveedor inicia en el Mes 4, con entrega formal al Departamento de TI en el Mes 8.

> Los costos de consumo de APIs de IA, licencias Azure, HubSpot, tokens y plataformas de terceros son responsabilidad de Salud S.A. Las tarifas de mantenimiento cubren únicamente servicios técnicos, soporte y actualizaciones del proveedor.

## 🔒 Confidencialidad

Documento interno · Confidencial · Dirigido a Salud S.A. — Innovación & Tecnología.
