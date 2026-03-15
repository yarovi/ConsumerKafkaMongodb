package org.yasmani.io.consumerkafkamongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.yasmani.io.consumerkafkamongodb.domain.ReservaConfirmadaDocument;

public interface ReservaConfirmadaRepository
        extends MongoRepository<ReservaConfirmadaDocument, String> {
}
