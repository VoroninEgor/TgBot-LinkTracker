package edu.java.kafka.config;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.link.LinkUpdateRequest;
import edu.java.kafka.service.ScrapperQueueProducer;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@Slf4j
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public NewTopic topic(ApplicationConfig config) {
        return TopicBuilder.name(config.topic())
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public ProducerFactory<String, LinkUpdateRequest> producerFactory(
        KafkaProperties kafkaProperties, ApplicationConfig config
    ) {
        Map<String, Object> configProps = kafkaProperties.buildProducerProperties(null);

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, config.producerClientId());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate(
        ProducerFactory<String, LinkUpdateRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ScrapperQueueProducer dataSender(KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate, NewTopic topic) {
        return new ScrapperQueueProducer(kafkaTemplate, topic.name());
    }
}
