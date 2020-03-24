package com.test.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import com.test.demo.configuration.KafkaConfigProperties;
import com.test.demo.configuration.KafkaConfiguration;

@Component
public class KafkaTest {

    private KafkaTemplate<Object, Object> kafkaTemplate;
    private KafkaConfiguration kafkaConfiguration;
    private KafkaConfigProperties kafkaConfigProperties;

    public KafkaTest(KafkaConfiguration kafkaConfiguration,
            KafkaConfigProperties kafkaConfigProperties) {
        this.kafkaConfiguration = kafkaConfiguration;
        this.kafkaConfigProperties = kafkaConfigProperties;
        this.kafkaTemplate = myKafkaTemplate();
    }

    public void sendMessageToKafka(String message) {
        kafkaTemplate.send(kafkaConfigProperties.getRetryTopic(), message);
        System.out.println("Message send successfully to kafka topic");
    }

    public ProducerFactory<Object, Object> myProducerFactory() {
        Map<String, Object> map = new HashMap<>();
        Properties producerProperties = kafkaConfiguration.getProducerProperties(kafkaConfigProperties);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.forEach((key, value) -> {
            map.put((String) key, value);
        });
        return new DefaultKafkaProducerFactory<>(map);
    }

    public KafkaTemplate<Object, Object> myKafkaTemplate() {
        return new KafkaTemplate<>(myProducerFactory());
    }
}
