package org.yasmani.io.consumerkafkamongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.yasmani.avro.reserva.v1.ReservaCreada;
import org.yasmani.io.consumerkafkamongodb.domain.ReservaCreadaDocument;

@Mapper(componentModel = "spring")
public interface ReservaCreatedMapper {


    @Mapping(
            target = "estado",
            expression = "java(event.getEstado().name())"
    )
    ReservaCreadaDocument toDocument(ReservaCreada event);
}
