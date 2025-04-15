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
@Profile("simulator")
public class InputPowerSimulator implements Simulator {

    private final KafkaDeviceDataProducer kafkaProducerService;
    private final Random random = new Random();

    public InputPowerSimulator(KafkaDeviceDataProducer kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void simulate() {
        Flux.interval(Duration.ofSeconds(5))
                .flatMap(tick -> {
                    long timestamp = Instant.now().toEpochMilli();
                    double power = 700.0 + random.nextDouble() * 200.0;

                    DeviceData data = DeviceData.builder()
                            .deviceId("pump-01")
                            .sensorId("power-in-001")
                            .location("motor")
                            .type("input_power")
                            .timestamp(timestamp)
                            .authKey("secret-key")
                            .data(Map.of(
                                    "value", power,
                                    "unit", "W"
                            ))
                            .build();

                    return kafkaProducerService.send("iot.device-data", data);
                })
                .subscribe();
    }
}
