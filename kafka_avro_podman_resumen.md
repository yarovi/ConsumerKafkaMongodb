# 🧩 Proyecto Kafka + Schema Registry + Podman (Resumen Completo)

## 🎯 Objetivo
Implementar un entorno local para eventos con:
- Apache Kafka
- Zookeeper
- Schema Registry (Avro)
- Kafka UI
- MongoDB

Usando Docker/Podman Compose.

---

## 🐳 Arquitectura

Servicios:
- zookeeper
- kafka
- schema-registry
- kafka-ui
- mongodb

Red: `reservas-net`

---

## ⚙️ docker-compose.yml (versión base)

```yaml
version: "3.8"

services:

  zookeeper:
    image: docker.io/confluentinc/cp-zookeeper:7.6.1
    ports:
      - "2181:2181"

  kafka:
    image: docker.io/confluentinc/cp-kafka:7.6.1
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092

  schema-registry:
    image: docker.io/confluentinc/cp-schema-registry:7.6.1
    depends_on:
      - kafka
    ports:
      - "8081:8081"
    environment:
      SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS: PLAINTEXT://kafka:9092

  kafka-ui:
    image: docker.io/provectuslabs/kafka-ui:latest
    ports:
      - "8085:8080"

  mongodb:
    image: docker.io/library/mongo:7
    ports:
      - "27017:27017"
```

---

## 🧠 Conceptos Clave

### 🔹 Topic vs Subject

| Concepto | Descripción |
|--------|-------------|
| Topic | Cola de mensajes en Kafka |
| Subject | Schema en Schema Registry |

Relación:
```
topic = reservas.confirmadas
subject = reservas.confirmadas-value
```

---

### 🔹 Schema Registry NO es stateless

- Guarda versiones de schemas
- No sobrescribe automáticamente
- Valida compatibilidad

---

### 🔹 Compatibilidad (por defecto BACKWARD)

✔ Permite agregar campos  
❌ NO permite cambiar tipos

Ejemplo inválido:
```
string → long ❌
```

---

## 🚨 Problema encontrado

Error:
```
SerializationException
ClassCastException: Instant → long
```

### 🔍 Causa
Schema v1 fue registrado incorrectamente.

### ✅ Solución

```bash
curl -X DELETE http://localhost:8081/subjects/reservas.confirmadas-value
```

---

## 🧪 Verificación

```bash
curl http://localhost:8081/subjects
curl http://localhost:8081/subjects/reservas.confirmadas-value/versions
```

---

## 📦 Envío de mensajes (Avro)

```bash
kafka-avro-console-producer --broker-list kafka:9092 --topic reservas.confirmadas --property schema.registry.url=http://schema-registry:8081 --property value.schema='{
 "type":"record",
 "name":"ReservaConfirmada",
 "fields":[
   {"name":"eventId","type":"string"},
   {"name":"reservationId","type":"string"},
   {"name":"fechaConfirmacion","type":{"type":"long","logicalType":"timestamp-millis"}}
 ]}'
```

Mensaje válido:

```json
{"eventId":"evt-1","reservationId":"res-100","fechaConfirmacion":1710000000000}
```

---

## 🔥 Uso de Topics para versionado

```bash
reservas.confirmadas.v1
reservas.confirmadas.v2
```

Cada topic genera un nuevo subject.

---

## 🌐 Problema de conectividad

### Caso 1: Desde host
```
localhost:9092 ✔
```

### Caso 2: Desde contenedor
```
localhost ❌
kafka:9092 ✔
```

---

## 🧠 Configuración recomendada

Para contenedores:

```yaml
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
```

Para host:

```yaml
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
```

---

## 🚀 Buenas prácticas (nivel TL)

- No cambiar tipos en Avro
- Versionar eventos correctamente
- Separar topic por versión si hay ruptura
- Validar contracts entre microservicios

---

## 🎯 Estado final

✔ Kafka operativo  
✔ Schema Registry funcionando  
✔ Avro validado  
✔ Producción de eventos OK  

---

## 📌 Próximos pasos

- Integrar Spring Boot con Avro
- Implementar Consumer
- Manejo de errores
- Evolución de schemas

---

🚀 Proyecto listo para escalar a arquitectura event-driven profesional.
