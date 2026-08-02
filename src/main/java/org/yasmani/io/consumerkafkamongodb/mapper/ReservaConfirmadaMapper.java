package org.yasmani.io.consumerkafkamongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.yasmani.avro.reserva.v1.ReservaConfirmada;
import org.yasmani.io.consumerkafkamongodb.domain.ReservaConfirmadaDocument;


@Mapper(componentModel = "spring")
public interface ReservaConfirmadaMapper {

  @Mapping(
      target = "canalConfirmacion",
      expression = "java(event.getCanalConfirmacion().name())"
  )
  ReservaConfirmadaDocument toDocument(ReservaConfirmada event);

}
