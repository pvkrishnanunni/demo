package com.test.demo.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class RetryKafkaListener {

    @KafkaListener(topics = "${spring.kafka.retry.topic}", groupId = "${spring.kafka.retry.consumer-group-id}",
            containerFactory = "kafkaRetryListenerContainerFactory", id = "price.retry.listener")
    public void processRetry(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            Thread.sleep(30000);
            System.out.println(String.format("Consumed retry message -> %s", record.toString()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ack.acknowledge();
    }
}
