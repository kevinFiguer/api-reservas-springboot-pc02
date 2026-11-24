package com.empresa.reservas.service;

import com.empresa.reservas.dto.ReporteDTO;
import com.empresa.reservas.dto.ReservaDTO;
import com.empresa.reservas.exception.NegocioException;
import com.empresa.reservas.model.Reserva;
import com.empresa.reservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva crearReserva(ReservaDTO dto) {
        // Regla 1: No se puede registrar una reserva para una fecha pasada
        if (dto.getFechaReserva().isBefore(LocalDate.now())) {
            throw new NegocioException("No se puede registrar una reserva para una fecha pasada.");
        }

        // Regla 2: La cantidad máxima permitida es de 20 participantes
        if (dto.getCantidadParticipantes() > 20) {
            throw new NegocioException("La cantidad máxima permitida es de 20 participantes.");
        }

        // Regla 3: La sala "Gerencia" admite como máximo 5 participantes
        if ("Gerencia".equalsIgnoreCase(dto.getSala().trim()) && dto.getCantidadParticipantes() > 5) {
            throw new NegocioException("La sala 'Gerencia' admite como máximo 5 participantes.");
        }

        Reserva nuevaReserva = new Reserva(
                null,
                dto.getSolicitante(),
                dto.getCorreo(),
                dto.getSala().trim(),
                dto.getFechaReserva(),
                dto.getCantidadParticipantes()
        );

        return reservaRepository.save(nuevaReserva);
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new NegocioException("No se encontró ninguna reserva con el ID: " + id));
    }

    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new NegocioException("No se puede eliminar. No existe reserva con el ID: " + id);
        }
        reservaRepository.deleteById(id);
    }

    public ReporteDTO generarReporte() {
        List<Reserva> todas = reservaRepository.findAll();
        long totalReservas = todas.size();

        String salaMasReservada = todas.stream()
                .collect(Collectors.groupingBy(Reserva::getSala, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Ninguna");

        return new ReporteDTO(totalReservas, salaMasReservada);
    }
}