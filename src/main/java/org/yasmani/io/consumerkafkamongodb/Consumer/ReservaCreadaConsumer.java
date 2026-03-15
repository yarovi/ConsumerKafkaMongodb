package org.yasmani.io.consumerkafkamongodb.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.yasmani.avro.reserva.v1.ReservaConfirmada;
import org.yasmani.avro.reserva.v1.ReservaCreada;
import org.yasmani.io.consumerkafkamongodb.domain.ReservaCreadaDocument;
import org.yasmani.io.consumerkafkamongodb.mapper.ReservaCreatedMapper;
import org.yasmani.io.consumerkafkamongodb.repository.ReservaCreadaRepository;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservaCreadaConsumer {

    private final ReservaCreadaRepository repository;

    private final ReservaCreatedMapper mapper;

    @KafkaListener(
            topics = "reservas.creadas",
            groupId = "reservas-consumer-group"
    )
    public void consume(ReservaCreada event) {

        log.info("Reserva creada recibida: {}", event.getReservationId());

        ReservaCreadaDocument doc = mapper.toDocument(event);
        doc.setMetadata(event.getMetadata());

        repository.save(doc);
    }
}
