package org.yasmani.io.consumerkafkamongodb.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.yasmani.io.consumerkafkamongodb.domain.ReservaConfirmadaDocument;
import org.yasmani.io.consumerkafkamongodb.mapper.ReservaConfirmadaMapper;
import org.yasmani.io.consumerkafkamongodb.repository.ReservaConfirmadaRepository;
import org.yasmani.avro.reserva.v1.ReservaConfirmada;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservaConfirmadaConsumer {

    private final ReservaConfirmadaRepository repository;

    private final ReservaConfirmadaMapper mapper;
    @KafkaListener(
            topics = "reservas.confirmadas",
            groupId = "reservas-consumer-group"
    )
    public void consume(ReservaConfirmada event) {

        log.info("Reserva confirmada recibida: {}", event.getReservationId());

        ReservaConfirmadaDocument doc = mapper.toDocument(event);

        repository.save(doc);
    }
}
