package edu.java.kafka.service;

import edu.java.AbstractIntegrationTest;
import edu.java.dto.link.LinkUpdateRequest;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ScrapperQueueProducerTest extends AbstractIntegrationTest {

    @Autowired
    DataSender scrapperQueueProducer;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("app.use-queue", () -> "true");
    }

    @Test
    void send() {
        LinkUpdateRequest update = LinkUpdateRequest.builder()
            .id(1L)
            .url(URI.create("url"))
            .tgChatIds(List.of(1L, 2L))
            .description("desc")
            .build();

        assertDoesNotThrow(() -> scrapperQueueProducer.send(update));

        await().pollInterval(Duration.ofSeconds(2)).atMost(6, TimeUnit.SECONDS).untilAsserted(() -> {});
    }
}
