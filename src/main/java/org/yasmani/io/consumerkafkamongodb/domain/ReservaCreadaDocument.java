package org.yasmani.io.consumerkafkamongodb.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;
@Data
@Document(collection = "reservas_creadas")
public class ReservaCreadaDocument {

    @Id
    private String eventId;
    private String reservationId;
    private int clienteId;
    private Long fechaReserva;
    private String estado;
    private Map<String, String> metadata;
}

