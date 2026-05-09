package ru.yandex.practicum.collector.gRPC.builders.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.collector.gRPC.producer.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class DeviceAddedBuilder extends BaseHubBuilder {
    public DeviceAddedBuilder(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public HubEventAvro toAvro(HubEventProto hubEvent) {
        DeviceAddedEventProto deviceAddedEvent = hubEvent.getDeviceAdded();

        return HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setTimestamp(mapTimestampToInstant(hubEvent))
                .setPayload(new DeviceAddedEventAvro(deviceAddedEvent.getId(), mapToDeviceTypeAvro(deviceAddedEvent.getDeviceType())))
                .build();
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    private DeviceTypeAvro mapToDeviceTypeAvro(DeviceTypeProto deviceType) {
        DeviceTypeAvro type = null;

        switch (deviceType) {
            case LIGHT_SENSOR -> type = DeviceTypeAvro.LIGHT_SENSOR;
            case MOTION_SENSOR -> type = DeviceTypeAvro.MOTION_SENSOR;
            case SWITCH_SENSOR -> type = DeviceTypeAvro.SWITCH_SENSOR;
            case CLIMATE_SENSOR -> type = DeviceTypeAvro.CLIMATE_SENSOR;
            case TEMPERATURE_SENSOR -> type = DeviceTypeAvro.TEMPERATURE_SENSOR;
        }

        return type;
    }
}
