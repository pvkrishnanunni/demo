package com.test.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfigProperties {

    @Value("${spring.kafka.client-id}")
    private String clientId;

    @Value("${spring.kafka.primary.consumer-group-id}")
    private String primaryConsumerGroupId;

    @Value("${spring.kafka.retry.consumer-group-id}")
    private String retryConsumerGroupId;

    @Value("${spring.kafka.dl.consumer-group-id}")
    private String dlConsumerGroupId;

    @Value("${spring.kafka.consumer-auto-offset-reset:earliest}")
    private String consumerAutoOffsetReset;

    @Value("${spring.kafka.schema-registry-url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.ssl-endpoint-identification-algorithm:https}")
    private String sslEndpointIdentificationAlgorithm;

    @Value("${spring.kafka.sasl-mechanism:PLAIN}")
    private String saslMechanism;

    @Value("${spring.kafka.request-timeout-ms:20000}")
    private String requestTimeOutMs;

    @Value("${spring.kafka.retry-backoff-ms:500}")
    private String retryBackoffMs;

    @Value("${spring.kafka.jaas-config}")
    private String jaasConfig;

    @Value("${spring.kafka.security-protocol:SASL_SSL}")
    private String securityProtocol;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.acks:all}")
    private String acks;

    @Value("${spring.kafka.retries:0}")
    private String retries;

    @Value("${spring.kafka.batch-size:16384}")
    private String batchSize;

    @Value("${spring.kafka.linger-ms:1}")
    private String lingerMs;

    @Value("${spring.kafka.buffer-memory:33554432}")
    private String bufferMemory;

    @Value("${spring.kafka.consumer.concurrency}")
    private Integer consumerConcurrency;

    @Value("${spring.kafka.consumer.enable-auto-commit:false}")
    private String autoCommit;

    @Value("${spring.kafka.primary.topic}")
    private String primaryTopic;

    @Value("${spring.kafka.retry.topic}")
    private String retryTopic;

    @Value("${spring.kafka.dl.topic}")
    private String deadLetterTopic;

    @Value("${spring.kafka.primary.fixed.backoff.time:1000}")
    private Integer primaryFixedBackoffTime;

    @Value("${spring.kafka.retry.fixed.backoff.time:1000}")
    private Integer retryFixedBackoffTime;

    @Value("${spring.kafka.retry.max.poll.interval:1200000}")
    private Integer retryMaxPollInterval;

    @Value("${spring.kafka.retry.interval.offset:300000}")
    private Integer retryIntervalOffset;

    @Value("${spring.kafka.price.retry.delay:900000}")
    private Integer priceRetryDelay;

    @Value("${spring.kafka.dl.consumer.poll.duration:5000}")
    private Integer dlConsumerPollDuration;

    @Value("${spring.kafka.dl.consumer.ack.duration:10000}")
    private Integer dlConsumerAckDuration;

    @Value("${spring.kafka.dl.consumer.asyncCommit:false}")
    private Boolean dlConsumerAsyncCommit;

    public String getClientId() {
        return clientId;
    }

    public String getPrimaryConsumerGroupId() {
        return primaryConsumerGroupId;
    }

    public String getRetryConsumerGroupId() {
        return retryConsumerGroupId;
    }

    public String getDlConsumerGroupId() {
        return dlConsumerGroupId;
    }

    public String getConsumerAutoOffsetReset() {
        return consumerAutoOffsetReset;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    public String getSslEndpointIdentificationAlgorithm() {
        return sslEndpointIdentificationAlgorithm;
    }

    public String getSaslMechanism() {
        return saslMechanism;
    }

    public String getRequestTimeOutMs() {
        return requestTimeOutMs;
    }

    public String getRetryBackoffMs() {
        return retryBackoffMs;
    }

    public String getJaasConfig() {
        return jaasConfig;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getAcks() {
        return acks;
    }

    public String getRetries() {
        return retries;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public String getLingerMs() {
        return lingerMs;
    }

    public String getBufferMemory() {
        return bufferMemory;
    }

    public Integer getConsumerConcurrency() {
        return consumerConcurrency;
    }

    public String getAutoCommit() {
        return autoCommit;
    }

    public String getPrimaryTopic() {
        return primaryTopic;
    }

    public String getRetryTopic() {
        return retryTopic;
    }

    public String getDeadLetterTopic() {
        return deadLetterTopic;
    }

    public Integer getPrimaryFixedBackoffTime() {
        return primaryFixedBackoffTime;
    }

    public Integer getRetryFixedBackoffTime() {
        return retryFixedBackoffTime;
    }

    public Integer getRetryMaxPollInterval() {
        return retryMaxPollInterval;
    }

    public Integer getDlConsumerPollDuration() {
        return dlConsumerPollDuration;
    }

    public Integer getDlConsumerAckDuration() {
        return dlConsumerAckDuration;
    }

    public Boolean getDlConsumerAsyncCommit() {
        return dlConsumerAsyncCommit;
    }

    public Integer getRetryIntervalOffset() {
        return retryIntervalOffset;
    }

    public Integer getPriceRetryDelay() {
        return priceRetryDelay;
    }
}
