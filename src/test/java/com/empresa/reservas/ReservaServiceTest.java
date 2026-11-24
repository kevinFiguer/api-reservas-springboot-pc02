package com.empresa.reservas;

import com.empresa.reservas.dto.ReservaDTO;
import com.empresa.reservas.exception.NegocioException;
import com.empresa.reservas.model.Reserva;
import com.empresa.reservas.repository.ReservaRepository;
import com.empresa.reservas.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void testRegistroValido() {
        ReservaDTO dto = new ReservaDTO();
        dto.setSolicitante("Kevin Figueroa");
        dto.setCorreo("kevin@empresa.com");
        dto.setSala("Sala A");
        dto.setFechaReserva(LocalDate.now().plusDays(1));
        dto.setCantidadParticipantes(10);

        Reserva reservaGuardada = new Reserva(1L, "Kevin Figueroa", "kevin@empresa.com", "Sala A", dto.getFechaReserva(), 10);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);

        Reserva resultado = reservaService.crearReserva(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sala A", resultado.getSala());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void testFechaInvalida() {
        ReservaDTO dto = new ReservaDTO();
        dto.setSolicitante("Ana Torres");
        dto.setCorreo("ana@empresa.com");
        dto.setSala("Sala B");
        dto.setFechaReserva(LocalDate.now().minusDays(1)); // Fecha pasada
        dto.setCantidadParticipantes(5);

        assertThrows(NegocioException.class, () -> reservaService.crearReserva(dto));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void testSalaGerenciaExcedeCapacidad() {
        ReservaDTO dto = new ReservaDTO();
        dto.setSolicitante("Carlos Ruiz");
        dto.setCorreo("carlos@empresa.com");
        dto.setSala("Gerencia");
        dto.setFechaReserva(LocalDate.now().plusDays(2));
        dto.setCantidadParticipantes(6); // Máximo permitido en Gerencia es 5

        assertThrows(NegocioException.class, () -> reservaService.crearReserva(dto));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void testParticipantesExcedenMaximoPermitido() {
        ReservaDTO dto = new ReservaDTO();
        dto.setSolicitante("Luis Gómez");
        dto.setCorreo("luis@empresa.com");
        dto.setSala("Sala Principal");
        dto.setFechaReserva(LocalDate.now().plusDays(3));
        dto.setCantidadParticipantes(21); // Máximo general es 20

        assertThrows(NegocioException.class, () -> reservaService.crearReserva(dto));
        verify(reservaRepository, never()).save(any());
    }
}