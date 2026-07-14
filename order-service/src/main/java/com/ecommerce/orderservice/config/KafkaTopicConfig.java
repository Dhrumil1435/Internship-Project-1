package com.ecommerce.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class for declaring Kafka topics.
 */
@Configuration
public class KafkaTopicConfig {

    private static final int DEFAULT_PARTITIONS = 3;
    private static final short DEFAULT_REPLICAS = 1;

    /**
     * Declares the order-created topic.
     *
     * @return the NewTopic bean
     */
    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name("order-created")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }

    /**
     * Declares the inventory-reserved topic.
     *
     * @return the NewTopic bean
     */
    @Bean
    public NewTopic inventoryReservedTopic() {
        return TopicBuilder.name("inventory-reserved")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }

    /**
     * Declares the inventory-failed topic.
     *
     * @return the NewTopic bean
     */
    @Bean
    public NewTopic inventoryFailedTopic() {
        return TopicBuilder.name("inventory-failed")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }

    /**
     * Declares the payment-success topic.
     *
     * @return the NewTopic bean
     */
    @Bean
    public NewTopic paymentSuccessTopic() {
        return TopicBuilder.name("payment-success")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }

    /**
     * Declares the payment-failed topic.
     *
     * @return the NewTopic bean
     */
    @Bean
    public NewTopic paymentFailedTopic() {
        return TopicBuilder.name("payment-failed")
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }
}
