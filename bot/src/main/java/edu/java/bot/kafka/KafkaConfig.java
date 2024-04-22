package edu.java.bot.kafka;

import edu.java.bot.configuration.ApplicationConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic(ApplicationConfig config) {
        return TopicBuilder.name(config.topic())
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic topicDlq(ApplicationConfig config) {
        return TopicBuilder.name(config.topicDlq())
            .partitions(1)
            .replicas(1)
            .build();
    }
}
