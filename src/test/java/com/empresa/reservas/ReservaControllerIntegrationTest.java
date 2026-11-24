package com.empresa.reservas;

import com.empresa.reservas.model.Reserva;
import com.empresa.reservas.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservaRepository reservaRepository;

    @Test
    void testGetAutorizado() throws Exception {
        mockMvc.perform(get("/reservas")
                        .with(httpBasic("operador", "operador123")))
                .andExpect(status().isOk());
    }

    @Test
    void testPostAutorizado() throws Exception {
        String fechaFutura = LocalDate.now().plusDays(5).toString();
        String jsonBody = """
                {
                    "solicitante": "María Pérez",
                    "correo": "maria@empresa.com",
                    "sala": "Sala Norte",
                    "fechaReserva": "%s",
                    "cantidadParticipantes": 8
                }
                """.formatted(fechaFutura);

        mockMvc.perform(post("/reservas")
                        .with(httpBasic("operador", "operador123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.solicitante").value("María Pérez"));
    }

    @Test
    void testDeleteAutorizadoParaAdmin() throws Exception {
        Reserva guardada = reservaRepository.save(
                new Reserva(null, "Test Admin", "admin@empresa.com", "Sala Sur", LocalDate.now().plusDays(2), 4)
        );

        mockMvc.perform(delete("/reservas/" + guardada.getId())
                        .with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteDenegadoParaOperador() throws Exception {
        Reserva guardada = reservaRepository.save(
                new Reserva(null, "Test Operador", "op@empresa.com", "Sala Sur", LocalDate.now().plusDays(2), 4)
        );

        mockMvc.perform(delete("/reservas/" + guardada.getId())
                        .with(httpBasic("operador", "operador123")))
                .andExpect(status().isForbidden());
    }
}