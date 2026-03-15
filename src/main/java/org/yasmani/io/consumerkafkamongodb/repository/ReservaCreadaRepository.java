package org.yasmani.io.consumerkafkamongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.yasmani.io.consumerkafkamongodb.domain.ReservaCreadaDocument;

public interface ReservaCreadaRepository
        extends MongoRepository<ReservaCreadaDocument, String> {
}
