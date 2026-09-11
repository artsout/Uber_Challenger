package com.rideshare.com.rideshare.ride_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic rideRequestedTopic() {
        return TopicBuilder.name("ride.requested")
                .partitions(3) // 3 partições permitem que até 3 instâncias do seu app leiam em paralelo
                .replicas(1)   // 1 réplica é o padrão ideal para desenvolvimento local
                .build();
    }

    @Bean
    public NewTopic rideMatchedTopic() {
        return TopicBuilder.name("ride.matched")
                .partitions(3) // 3 partições permitem que até 3 instâncias do seu app leiam em paralelo
                .replicas(1)   // 1 réplica é o padrão ideal para desenvolvimento local
                .build();
    }
}
