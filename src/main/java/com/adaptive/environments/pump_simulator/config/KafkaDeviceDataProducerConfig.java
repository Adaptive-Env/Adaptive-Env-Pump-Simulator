package com.adaptive.environments.pump_simulator.config;

import com.adaptive.environments.pump_simulator.model.DeviceData;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaDeviceDataProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    @Bean
    public KafkaSender<String, DeviceData> kafkaSenderDeviceData() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        SenderOptions<String, DeviceData> options = SenderOptions.create(props);
        return KafkaSender.create(options);

    }
}
