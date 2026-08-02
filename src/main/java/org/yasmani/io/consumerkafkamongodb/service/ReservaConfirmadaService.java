package org.yasmani.io.consumerkafkamongodb.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yasmani.avro.reserva.v1.CanalConfirmacion;
import org.yasmani.avro.reserva.v1.EstadoReserva;
import org.yasmani.avro.reserva.v1.ReservaConfirmada;
import org.yasmani.avro.reserva.v1.ReservaCreada;
import org.yasmani.io.consumerkafkamongodb.producer.ReservaConfirmadaProducer;
import org.yasmani.io.consumerkafkamongodb.producer.ReservaCreadaProducer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ReservaConfirmadaService {

  private final ReservaCreadaProducer producer2;
  private final ReservaConfirmadaProducer producer;

  public ReservaConfirmada generarConfirmacionReservaEvento() {

    ReservaConfirmada event = ReservaConfirmada.newBuilder()
        .setEventId(UUID.randomUUID().toString())
        .setReservationId("RES-" + ThreadLocalRandom.current().nextInt(1000,9999))
        .setFechaConfirmacion(System.currentTimeMillis())
        .setCanalConfirmacion(obtenerCanal())
        .setMetadata(
            Map.of(
                "usuario","yasmani",
                "pais","PE"
            )
        )
        .build();

    producer.publish(event);

    return event;
  }

  public ReservaCreada solicitarReservaEvento(){
    ReservaCreada event= ReservaCreada.newBuilder()
        .setEventId(UUID.randomUUID().toString())
        .setReservationId("RES-" + ThreadLocalRandom.current().nextInt(1000,9999))
        .setClienteId((int) (Math.random() * 50) + 1)
        .setFechaReserva(System.currentTimeMillis())
        .setEstado(EstadoReserva.CREADA)
        .build();
    producer2.publish(event);
    return event;
  }

  private CanalConfirmacion obtenerCanal() {

    CanalConfirmacion[] values = CanalConfirmacion.values();

    return values[
        ThreadLocalRandom.current()
            .nextInt(values.length)
        ];
  }

}
