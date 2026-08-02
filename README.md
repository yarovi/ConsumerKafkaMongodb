# go to UI kafka
http://localhost:8085/


# launch container same point
podman run -it \
--network infra_kafka_reservas-net \
confluentinc/cp-schema-registry:7.6.1 bash

# Run command Productor

kafka-avro-console-producer \
--broker-list kafka:9092 \
--topic reservas.confirmadas \
--property schema.registry.url=http://schema-registry:8081 \
--property value.schema='{
"type":"record",
"name":"ReservaConfirmada",
"namespace":"org.yasmani.avro.reserva.v1",
"fields":[
{"name":"eventId","type":"string"},
{"name":"reservationId","type":"string"},
{"name":"fechaConfirmacion","type":{"type":"long","logicalType":"timestamp-millis"}},
{"name":"canalConfirmacion","type":{"type":"enum","name":"CanalConfirmacion","symbols":["WEB","MOBILE","CALL_CENTER"]}},
{"name":"metadata","type":["null",{"type":"map","values":"string"}],"default":null}
]}'

V2
kafka-avro-console-producer \
--broker-list kafka:9092 \
--topic examplev2 \
--property schema.registry.url=http://schema-registry:8081 \
--property value.schema='{
"type":"record",
"name":"ReservaConfirmada",
"namespace":"org.yasmani.avro.reserva.v1",
"fields":[
{"name":"eventId","type":"string"},
{"name":"reservationId","type":"string"},
{"name":"fechaConfirmacion","type":"long"},
{"name":"canalConfirmacion","type":{"type":"enum","name":"CanalConfirmacion","symbols":["WEB","MOBILE","CALL_CENTER"]}},
{"name":"metadata","type":["null",{"type":"map","values":"string"}],"default":null}
]}'

# before type:
{"eventId": "evt-1","reservationId": "res-100","fechaConfirmacion": 1710086400000,"canalConfirmacion": "WEB","metadata": null }
# Value Schema

{
"type": "record",
"name": "ReservaCreada",
"namespace": "org.yasmani.avro.reserva.v1",
"fields": [
{"name": "eventId", "type": "string"},
{"name": "reservationId", "type": "string"},
{"name": "clienteId", "type": "string"},
{
"name": "fechaReserva",
"type": {"type": "long", "logicalType": "timestamp-millis"}
},
{
"name": "estado",
"type": {
"type": "enum",
"name": "EstadoReserva",
"symbols": ["CREADA"]
}
},
{
"name": "metadata",
"type": ["null", {"type": "map", "values": "string"}],
"default": null
}
]
}

# Value
{
"eventId": "evt-1",
"reservationId": "res-100",
"clienteId": "cli-1",
"fechaReserva": 1710000000000,
"estado": "CREADA",
"metadata": null
}

## Generate Message 
curl -x POST http://localhost:8083/api/reservas/confirmar