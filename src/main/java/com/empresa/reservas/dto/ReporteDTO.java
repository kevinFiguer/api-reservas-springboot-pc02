package com.empresa.reservas.dto;

public class ReporteDTO {
    private long totalReservas;
    private String salaMasReservada;

    public ReporteDTO() {
    }

    public ReporteDTO(long totalReservas, String salaMasReservada) {
        this.totalReservas = totalReservas;
        this.salaMasReservada = salaMasReservada;
    }

    public long getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(long totalReservas) {
        this.totalReservas = totalReservas;
    }

    public String getSalaMasReservada() {
        return salaMasReservada;
    }

    public void setSalaMasReservada(String salaMasReservada) {
        this.salaMasReservada = salaMasReservada;
    }
}