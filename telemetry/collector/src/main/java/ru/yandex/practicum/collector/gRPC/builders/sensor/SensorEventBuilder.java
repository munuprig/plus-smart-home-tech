package ru.yandex.practicum.collector.gRPC.builders.sensor;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

public interface SensorEventBuilder {

    void builder(SensorEventProto sensorEvent);

    SensorEventProto.PayloadCase getMessageType();
}
