package org.yasmani.io.consumerkafkamongodb.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yasmani.avro.reserva.v1.ReservaConfirmada;
import org.yasmani.io.consumerkafkamongodb.service.ReservaConfirmadaService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reservas")
public class ReservaController {

  private final ReservaConfirmadaService service;

  @PostMapping("/confirmar")
  public ReservaConfirmada confirmar() {

    return service.generarEvento();

  }

}
