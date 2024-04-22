package edu.java.kafka.service;

import edu.java.dto.link.LinkUpdateRequest;
import edu.java.kafka.KafkaEnvTest;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ScrapperQueueProducerTest extends KafkaEnvTest{

    @Autowired
    ScrapperQueueProducer scrapperQueueProducer;

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
