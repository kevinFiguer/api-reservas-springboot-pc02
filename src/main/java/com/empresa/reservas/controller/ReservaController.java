package com.empresa.reservas.controller;

import com.empresa.reservas.dto.ReporteDTO;
import com.empresa.reservas.dto.ReservaDTO;
import com.empresa.reservas.model.Reserva;
import com.empresa.reservas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // RF01. Registrar una reserva
    @PostMapping
    public ResponseEntity<Reserva> registrarReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        Reserva creada = reservaService.crearReserva(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // RF02. Consultar todas las reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> consultarTodas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    // RF03. Consultar una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerReservaPorId(id));
    }

    // RF04. Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.ok(Map.of("mensaje", "Reserva eliminada correctamente"));
    }

    // RF05. Generar reporte de reservas
    @GetMapping("/reporte")
    public ResponseEntity<ReporteDTO> generarReporte() {
        return ResponseEntity.ok(reservaService.generarReporte());
    }
}