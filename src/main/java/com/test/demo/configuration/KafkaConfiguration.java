package com.test.demo.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@EnableKafka
public class KafkaConfiguration {

    private static final AtomicInteger CONSUMER_CLIENT_ID_SEQUENCE = new AtomicInteger(1);

    private static final String SSL_ENDPOINT_IDFN_ALGM = "ssl.endpoint.identification.algorithm";
    private static final String REQUEST_TIMEOUT = "request.timeout.ms";
    private static final String RETRY_BACKOFF_MS = "retry.backoff.ms";
    private static final String SECURITY_PROTOCOL = "security.protocol";
    private static final String SCHEMA_REGISTRY_URL = "schema.registry.url";
    private static final String MAX_POLL_INTERVAL = "max.poll.interval.ms";

    private KafkaConfigProperties kafkaConfigProperties;

    public KafkaConfiguration(KafkaConfigProperties kafkaConfigProperties) {
        this.kafkaConfigProperties = kafkaConfigProperties;
    }

    @Bean
    public ConsumerFactory<String, String> primaryConsumerFactory() {
        Map<String, Object> map = new HashMap<>();
        Properties consumerProperties = getConsumerProperties(this.kafkaConfigProperties);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigProperties.getPrimaryConsumerGroupId());
        consumerProperties.forEach((key, value) -> map.put((String) key, value));
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(map);
        consumerFactory.setValueDeserializer(new StringDeserializer());
        return consumerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(primaryConsumerFactory());
        factory.setConcurrency(this.kafkaConfigProperties.getConsumerConcurrency());
        factory.setAutoStartup(true);
        factory.getContainerProperties().setAckOnError(false);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaRetryListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(retryConsumerFactory());
        factory.setConcurrency(this.kafkaConfigProperties.getConsumerConcurrency());
        factory.setAutoStartup(true);
        factory.getContainerProperties().setAckOnError(false);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> retryConsumerFactory() {
        Map<String, Object> map = new HashMap<>();
        Properties consumerProperties = getConsumerProperties(this.kafkaConfigProperties);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigProperties.getRetryConsumerGroupId());
        consumerProperties.put(MAX_POLL_INTERVAL, this.kafkaConfigProperties.getRetryMaxPollInterval());
        consumerProperties.forEach((key, value) -> map.put((String) key, value));
        DefaultKafkaConsumerFactory<String, String> retryConsumerFactory = new DefaultKafkaConsumerFactory<>(map);
        retryConsumerFactory.setValueDeserializer(new StringDeserializer());
        return retryConsumerFactory;
    }

    private Properties getConsumerProperties(KafkaConfigProperties kafkaConfigProperties) {
        Properties properties = getCommonProperties(kafkaConfigProperties);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfigProperties.getConsumerAutoOffsetReset());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConfigProperties.getAutoCommit());
        return properties;
    }

    private Properties getCommonProperties(KafkaConfigProperties kafkaConfigProperties) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.getBootstrapServers());
        properties.put(SCHEMA_REGISTRY_URL, kafkaConfigProperties.getSchemaRegistryUrl());
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,
                kafkaConfigProperties.getClientId() + "-" + CONSUMER_CLIENT_ID_SEQUENCE.getAndIncrement());
        properties.put(SSL_ENDPOINT_IDFN_ALGM, kafkaConfigProperties.getSslEndpointIdentificationAlgorithm());
        properties.put(SaslConfigs.SASL_MECHANISM, kafkaConfigProperties.getSaslMechanism());
        properties.put(REQUEST_TIMEOUT, kafkaConfigProperties.getRequestTimeOutMs());
        properties.put(RETRY_BACKOFF_MS, kafkaConfigProperties.getRetryBackoffMs());
        properties.put(SaslConfigs.SASL_JAAS_CONFIG, kafkaConfigProperties.getJaasConfig());
        properties.put(SECURITY_PROTOCOL, kafkaConfigProperties.getSecurityProtocol());
        return properties;
    }

    public Properties getProducerProperties(KafkaConfigProperties kafkaConfigProperties) {
        Properties properties = getCommonProperties(kafkaConfigProperties);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, kafkaConfigProperties.getAcks());
        properties.put(ProducerConfig.RETRIES_CONFIG, kafkaConfigProperties.getRetries());
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaConfigProperties.getBatchSize());
        properties.put(ProducerConfig.LINGER_MS_CONFIG, kafkaConfigProperties.getLingerMs());
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaConfigProperties.getBufferMemory());
        return properties;
    }
}
