package edu.java.kafka;

import edu.java.scrapper.IntegrationTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class KafkaEnvTest extends IntegrationTest {
    public static KafkaContainer KAFKA;

    static {
        KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.2"));
        KAFKA.start();
    }

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    }
}
