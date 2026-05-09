package ru.yandex.practicum.analyzer.client;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.analyzer.model.Action;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;

import java.time.Instant;

@Slf4j
@Service
public class HubRouterClient {

    private final HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    public HubRouterClient(@GrpcClient("hub-router")
                           HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient) {
        this.hubRouterClient = hubRouterClient;
    }

    public void sendAction(Action action) {
        DeviceActionRequest deviceActionRequest = buildActionRequest(action);
        hubRouterClient.handleDeviceAction(deviceActionRequest);
        log.info("Действие {} отправлено в hub-router", deviceActionRequest);
    }

    private DeviceActionRequest buildActionRequest(Action action) {
        return DeviceActionRequest.newBuilder()
                .setHubId(action.getScenario().getHubId())
                .setScenarioName(action.getScenario().getName())
                .setAction(DeviceActionProto.newBuilder()
                        .setSensorId(action.getSensor().getId())
                        .setType(actionTypeProto(action.getType()))
                        .setValue(action.getValue())
                        .build())
                .setTimestamp(setTimestamp())
                .build();
    }

    private ActionTypeProto actionTypeProto(ActionTypeAvro actionType) {
        return switch (actionType) {
            case ACTIVATE -> ActionTypeProto.ACTIVATE;
            case DEACTIVATE -> ActionTypeProto.DEACTIVATE;
            case INVERSE -> ActionTypeProto.INVERSE;
            case SET_VALUE -> ActionTypeProto.SET_VALUE;
        };
    }

    private Timestamp setTimestamp() {
        Instant instant = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
