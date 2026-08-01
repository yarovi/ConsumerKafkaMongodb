package org.yasmani.io.consumerkafkamongodb.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.yasmani.avro.reserva.v1.ReservaConfirmada;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservaConfirmadaProducer {

  private static final String TOPIC = "reservas.confirmadas";

  private final KafkaTemplate<String, ReservaConfirmada> kafkaTemplate;

  public void publish(ReservaConfirmada event) {

    kafkaTemplate.send(
        TOPIC,
        event.getReservationId(),
        event
    );

    log.info(
        "Evento enviado. reservationId={}",
        event.getReservationId()
    );
  }
}
