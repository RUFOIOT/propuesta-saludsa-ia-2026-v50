package com.saludsa.salescopilot.domain;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Representa un prospecto (lead) capturado por el agente Sales Copilot.
 * Corresponde al entregable E1.1 (Lead Intelligence Agent) y E1.4
 * (Integracion HubSpot CRM) de la propuesta.
 */
@Entity
@Table(name = "leads")
public class Lead {

    public enum Status {
        NUEVO,
        CONTACTADO,
        CALIFICADO,
        PROPUESTA_ENVIADA,
        EN_NEGOCIACION,
        CERRADO_GANADO,
        CERRADO_PERDIDO
    }

    public enum Channel {
        WHATSAPP,
        TELEGRAM,
        WEB_FORM,
        REFERIDO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nombre;

    private String telefono;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Channel canalOrigen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status estado = Status.NUEVO;

    /** Score IA (0-100) calculado por LeadIntelligenceService. */
    private int scoreIa;

    /** Id del contacto/deal correspondiente en HubSpot (null hasta sincronizar). */
    private String hubspotDealId;

    @Column(nullable = false, updatable = false)
    private Instant creadoEn = Instant.now();

    private Instant actualizadoEn = Instant.now();

    protected Lead() {
        // JPA
    }

    public Lead(String nombre, String telefono, String email, Channel canalOrigen) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.canalOrigen = canalOrigen;
    }

    public void avanzarEstado(Status nuevoEstado) {
        this.estado = nuevoEstado;
        this.actualizadoEn = Instant.now();
    }

    public void asignarScore(int score) {
        this.scoreIa = Math.max(0, Math.min(100, score));
        this.actualizadoEn = Instant.now();
    }

    public void vincularHubspot(String dealId) {
        this.hubspotDealId = dealId;
        this.actualizadoEn = Instant.now();
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public Channel getCanalOrigen() { return canalOrigen; }
    public Status getEstado() { return estado; }
    public int getScoreIa() { return scoreIa; }
    public String getHubspotDealId() { return hubspotDealId; }
    public Instant getCreadoEn() { return creadoEn; }
    public Instant getActualizadoEn() { return actualizadoEn; }
}
