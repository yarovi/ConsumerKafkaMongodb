package org.yasmani.io.consumerkafkamongodb.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.yasmani.avro.reserva.v1.ReservaCreada;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservaCreadaProducer {

  private static final String TOPIC ="reservas.creadas";

  private final KafkaTemplate<String, ReservaCreada> kafkaTemplate;

  public void publish (ReservaCreada reservaCreada) {

    kafkaTemplate.send(TOPIC,reservaCreada.getReservationId(), reservaCreada);

    log.info("Reserva confirmada com sucesso creada: {}", reservaCreada.toString());
  }
}
