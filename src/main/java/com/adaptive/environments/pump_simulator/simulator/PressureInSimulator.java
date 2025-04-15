package com.adaptive.environments.pump_simulator.simulator;

import com.adaptive.environments.pump_simulator.kafka.KafkaDeviceDataProducer;
import com.adaptive.environments.pump_simulator.model.DeviceData;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Random;

@Component
public class PressureInSimulator implements Simulator {

    private final KafkaDeviceDataProducer kafkaProducerService;
    private final Random random = new Random();

    public PressureInSimulator(KafkaDeviceDataProducer kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void simulate() {
        Flux.interval(Duration.ofSeconds(5))
                .flatMap(tick -> {
                    long timestamp = Instant.now().toEpochMilli();
                    double pressure = 0.8 + random.nextDouble() * 0.7;

                    DeviceData data = DeviceData.builder()
                            .deviceId("pump-01")
                            .sensorId("pressure-in-001")
                            .location("inlet")
                            .type("pressure_in")
                            .timestamp(timestamp)
                            .authKey("secret-key")
                            .data(Map.of(
                                    "value", pressure,
                                    "unit", "bar"
                            ))
                            .build();

                    return kafkaProducerService.send("iot.device-data", data);
                })
                .subscribe();
    }
}
