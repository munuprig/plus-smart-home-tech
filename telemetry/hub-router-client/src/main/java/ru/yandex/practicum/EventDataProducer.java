package ru.yandex.practicum;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.*;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.*;

import java.time.Instant;
import java.util.Random;

@Component
@Slf4j
public class EventDataProducer {

    @GrpcClient("collector")
    private CollectorControllerGrpc.CollectorControllerBlockingStub collectorStub;

    private final Random random = new Random(System.currentTimeMillis());

    @Autowired
    private MultipleYamlConfiguration configuration;


    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void run() {
        log.info("start run");

        configuration.getClimateSensors()
                .forEach(i->{
                    var event = createClimateSensorEvent(i);
                    sendEvent(event);
                });

        configuration.getLightSensors()
                .forEach(i->{
                    var event = createLightSensorEvent(i);
                    sendEvent(event);
                });

        configuration.getMotionSensors()
                .forEach(i->{
            var event = createMotionSensorEvent(i);
            sendEvent(event);
        });

        configuration.getSwitchSensors()
                .forEach(i->{
                    var event = createSwitchSensorEvent(i);
                    sendEvent(event);
                });

        configuration.getTemperatureSensors()
                .forEach(i->{
                    var event = createTemperatureSensorEvent(i);
                    sendEvent(event);
                });
    }

    private SensorEventProto createClimateSensorEvent(ClimateSensorConfig sensor) {
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setClimateSensorEvent(
                        ClimateSensorProto.newBuilder()
                                .setTemperatureC(getRandomMinMax(sensor.getTemperature()))
                                .setHumidity(getRandomMinMax(sensor.getHumidity()))
                                .setCo2Level(getRandomMinMax(sensor.getCo2Level()))
                                .build()
                )
                .build();
    }

    private SensorEventProto createLightSensorEvent(LightSensorConfig sensor) {
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setLightSensorEvent(
                        LightSensorProto.newBuilder()
                                .setLinkQuality(getRandomMinMax(sensor.getLinkQuality()))
                                .setLuminosity(getRandomMinMax(sensor.getLuminosity()))
                                .build()
                )
                .build();
    }

    private SensorEventProto createMotionSensorEvent(MotionSensorConfig sensor) {
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setMotionSensorEvent(
                        MotionSensorProto.newBuilder()
                                .setLinkQuality(getRandomMinMax(sensor.getLinkQuality()))
                                .setMotion(getRandomBoolean())
                                .setVoltage(getRandomMinMax(sensor.getVoltage()))
                                .build()
                )
                .build();
    }

    private SensorEventProto createSwitchSensorEvent(SwitchSensorConfig sensor) {
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setSwitchSensorEvent(
                        SwitchSensorProto.newBuilder()
                                .setState(getRandomBoolean())
                                .build()
                )
                .build();
    }

    private SensorEventProto createTemperatureSensorEvent(TemperatureSensorConfig sensor) {
        int temperatureCelsius = getRandomMinMax(sensor.getTemperature());
        int temperatureFahrenheit = (int) (temperatureCelsius * 1.8 + 32);
        Instant ts = Instant.now();

        return SensorEventProto.newBuilder()
                .setId(sensor.getId())
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(ts.getEpochSecond())
                        .setNanos(ts.getNano())
                ).setTemperatureSensorEvent(
                        TemperatureSensorProto.newBuilder()
                                .setTemperatureC(temperatureCelsius)
                                .setTemperatureF(temperatureFahrenheit)
                                .build()
                )
                .build();
    }

    private int getRandomMinMax(MinMaxConfig input) {
        return random.nextInt(input.getMinValue(), input.getMaxValue());
    }

    private boolean getRandomBoolean() {
        return random.nextInt(0,1)==1;
    }

    private void sendEvent(SensorEventProto event) {
        log.info("Отправляю данные: {}", event.getAllFields());
        CollectorResponse response = collectorStub.collectSensorEvent(event);
        log.info("Получил ответ от коллектора: {}", response);
    }
}
