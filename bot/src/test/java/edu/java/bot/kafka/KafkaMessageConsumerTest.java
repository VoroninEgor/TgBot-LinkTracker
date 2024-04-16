package edu.java.bot.kafka;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.service.UpdatesService;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

class KafkaMessageConsumerTest extends KafkaEnvTest{

    @MockBean
    UpdatesService updatesService;
    @Autowired
    KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @Test
    void consume() {
        LinkUpdateRequest update = new LinkUpdateRequest(1L, URI.create("url"), "desc", List.of(1L));
        kafkaTemplate.send("updates-topic", update);

        await().pollInterval(Duration.ofSeconds(2)).atMost(6, TimeUnit.SECONDS).untilAsserted(() -> {
            verify(updatesService).handleUpdate(update);
        });
    }
}
