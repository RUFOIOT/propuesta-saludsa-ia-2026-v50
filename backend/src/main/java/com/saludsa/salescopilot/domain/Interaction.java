package com.saludsa.salescopilot.domain;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Registro de una interaccion del agente con un lead (mensaje entrante/saliente,
 * comando de bot ejecutado, etc). Alimenta la capa de trazabilidad equivalente
 * al tab "Interacciones" de Google Sheets descrito en la propuesta (E1.7).
 */
@Entity
@Table(name = "interactions")
public class Interaction {

    public enum Direction {
        ENTRANTE,
        SALIENTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String leadId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Lead.Channel canal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Direction direccion;

    /** Comando de bot asociado, si aplica: propuesta / cotizar / seguimiento / agenda / briefing. */
    private String comandoBot;

    @Column(length = 2000)
    private String contenido;

    @Column(nullable = false, updatable = false)
    private Instant timestamp = Instant.now();

    protected Interaction() {
        // JPA
    }

    public Interaction(String leadId, Lead.Channel canal, Direction direccion, String comandoBot, String contenido) {
        this.leadId = leadId;
        this.canal = canal;
        this.direccion = direccion;
        this.comandoBot = comandoBot;
        this.contenido = contenido;
    }

    public String getId() { return id; }
    public String getLeadId() { return leadId; }
    public Lead.Channel getCanal() { return canal; }
    public Direction getDireccion() { return direccion; }
    public String getComandoBot() { return comandoBot; }
    public String getContenido() { return contenido; }
    public Instant getTimestamp() { return timestamp; }
}
