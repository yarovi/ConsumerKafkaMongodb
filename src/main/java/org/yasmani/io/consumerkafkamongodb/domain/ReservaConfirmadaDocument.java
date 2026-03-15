package org.yasmani.io.consumerkafkamongodb.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;
@Data
@Document(collection = "reservas_confirmadas")
public class ReservaConfirmadaDocument {

    @Id
    private String eventId;
    private String reservationId;
    private Instant fechaConfirmacion;
    private String canalConfirmacion;
    private Map<String, String> metadata;
}

