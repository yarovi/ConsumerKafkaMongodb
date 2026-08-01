package org.yasmani.io.consumerkafkamongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.yasmani.avro.reserva.v1.ReservaConfirmada;
import org.yasmani.io.consumerkafkamongodb.domain.ReservaConfirmadaDocument;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface ReservaConfirmadaMapper {

    @Mapping(
            target = "canalConfirmacion",
            expression = "java(event.getCanalConfirmacion().name())"
    )
    //@Mapping(target = "fechaConfirmacion", source = "fechaConfirmacion", qualifiedByName = "instantToLong")
    ReservaConfirmadaDocument toDocument(ReservaConfirmada event);

    @Named("instantToLong")
    default Long instantToLong(Instant instant) {
        return instant != null ? instant.toEpochMilli() : null;
    }
}
