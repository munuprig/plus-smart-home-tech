package ru.yandex.practicum.collector.gRPC.controller;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.yandex.practicum.collector.gRPC.builders.hub.HubEventBuilder;
import ru.yandex.practicum.collector.gRPC.builders.sensor.SensorEventBuilder;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.CollectorResponse;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
public class CollectorController extends CollectorControllerGrpc.CollectorControllerImplBase {

    private final Map<SensorEventProto.PayloadCase, SensorEventBuilder> sensorEventBuilders;
    private final Map<HubEventProto.PayloadCase, HubEventBuilder> hubEventBuilders;

    public CollectorController(Set<SensorEventBuilder> sensorEventBuilders, Set<HubEventBuilder> hubEventBuilders) {
        this.sensorEventBuilders = sensorEventBuilders.stream()
                .collect(Collectors.toMap(
                        SensorEventBuilder::getMessageType,
                        Function.identity()
                ));
        this.hubEventBuilders = hubEventBuilders.stream()
                .collect(Collectors.toMap(
                        HubEventBuilder::getMessageType,
                        Function.identity()
                ));
    }

    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<CollectorResponse> responseObserver) {
        try {
            if (sensorEventBuilders.containsKey(request.getPayloadCase())) {
                sensorEventBuilders.get(request.getPayloadCase()).builder(request);
            } else {
                throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getPayloadCase());
            }
            responseObserver.onNext(CollectorResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.fromThrowable(e)
            ));
        }
    }

    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<CollectorResponse> responseObserver) {
        try {
            if (hubEventBuilders.containsKey(request.getPayloadCase())) {
                hubEventBuilders.get(request.getPayloadCase()).builder(request);
            } else {
                throw new IllegalArgumentException("Не могу найти обработчик для события " + request.getPayloadCase());
            }
            responseObserver.onNext(CollectorResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(
                    Status.fromThrowable(e)
            ));
        }
    }
}
