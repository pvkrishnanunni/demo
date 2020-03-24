package com.test.demo.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PrimaryKafkaListener {

    @KafkaListener(topics = "${spring.kafka.primary.topic}", groupId = "${spring.kafka.primary.consumer-group-id}",
            containerFactory = "kafkaListenerContainerFactory", id = "price-primary-listener")
    public void processPriceMessage(String message) {
        System.out.println("Consuming message: "+ message);
    }
}
