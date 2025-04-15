package com.adaptive.environments.pump_simulator.kafka;

import com.adaptive.environments.pump_simulator.model.DeviceData;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Component
public class KafkaDeviceDataProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaDeviceDataProducer.class);

    private final KafkaSender<String, DeviceData> kafkaSender;

    public KafkaDeviceDataProducer(KafkaSender<String, DeviceData> kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    public Mono<Void> send(String topic, DeviceData data) {
        ProducerRecord<String, DeviceData> producerRecord = new ProducerRecord<>(topic, data.getDeviceId(), data);
        SenderRecord<String, DeviceData, String> record = SenderRecord.create(producerRecord, null);

        return kafkaSender.send(Flux.just(record))
                .doOnNext(result -> {
                    log.info("[Kafka] Successfully sent {}", data.toString());
                })
                .then();
    }
}