package edu.java.bot.kafka;

import edu.java.bot.AbstractIntegrationTest;
import edu.java.bot.dto.LinkUpdateRequest;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

class KafkaMessageConsumerTest extends AbstractIntegrationTest {

    @Autowired
    KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @Test
    void consume() {
        LinkUpdateRequest update = new LinkUpdateRequest(1L, URI.create("url"), "desc", List.of(1L));
        kafkaTemplate.send("updates-topic", update);
    }
}
