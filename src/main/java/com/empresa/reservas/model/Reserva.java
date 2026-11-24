package com.empresa.reservas.model;

import java.time.LocalDate;

public class Reserva {
    private Long id;
    private String solicitante;
    private String correo;
    private String sala;
    private LocalDate fechaReserva;
    private Integer cantidadParticipantes;

    public Reserva() {
    }

    public Reserva(Long id, String solicitante, String correo, String sala, LocalDate fechaReserva, Integer cantidadParticipantes) {
        this.id = id;
        this.solicitante = solicitante;
        this.correo = correo;
        this.sala = sala;
        this.fechaReserva = fechaReserva;
        this.cantidadParticipantes = cantidadParticipantes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Integer getCantidadParticipantes() {
        return cantidadParticipantes;
    }

    public void setCantidadParticipantes(Integer cantidadParticipantes) {
        this.cantidadParticipantes = cantidadParticipantes;
    }
}