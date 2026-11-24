package com.empresa.reservas.repository;

import com.empresa.reservas.model.Reserva;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservaRepository {

    private final Map<Long, Reserva> almacenamiento = new ConcurrentHashMap<>();
    private final AtomicLong generadorId = new AtomicLong(1);

    public Reserva save(Reserva reserva) {
        if (reserva.getId() == null) {
            reserva.setId(generadorId.getAndIncrement());
        }
        almacenamiento.put(reserva.getId(), reserva);
        return reserva;
    }

    public List<Reserva> findAll() {
        return new ArrayList<>(almacenamiento.values());
    }

    public Optional<Reserva> findById(Long id) {
        return Optional.ofNullable(almacenamiento.get(id));
    }

    public boolean existsById(Long id) {
        return almacenamiento.containsKey(id);
    }

    public void deleteById(Long id) {
        almacenamiento.remove(id);
    }
}