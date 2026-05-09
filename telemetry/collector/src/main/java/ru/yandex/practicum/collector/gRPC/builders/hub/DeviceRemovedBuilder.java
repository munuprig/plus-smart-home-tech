package ru.yandex.practicum.collector.gRPC.builders.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.gRPC.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class DeviceRemovedBuilder extends BaseHubBuilder {
    public DeviceRemovedBuilder(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public HubEventAvro toAvro(HubEventProto hubEvent) {
        DeviceRemovedEventProto deviceRemovedEvent = hubEvent.getDeviceRemoved();

        return HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setTimestamp(mapTimestampToInstant(hubEvent))
                .setPayload(new DeviceRemovedEventAvro(deviceRemovedEvent.getId()))
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }
}
