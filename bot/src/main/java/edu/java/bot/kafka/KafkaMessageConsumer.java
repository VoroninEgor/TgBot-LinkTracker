package edu.java.bot.kafka;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.service.UpdatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageConsumer {

    @Autowired
    private final UpdatesService updatesService;

    @KafkaListener(topics = "${spring.kafka.topic}",
                   groupId = "${spring.kafka.consumer.group-id}",
                   properties = {"spring.json.value.default.type=edu.java.bot.dto.LinkUpdateRequest"})
    public void consume(LinkUpdateRequest linkUpdate, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received update: {} from {}", linkUpdate, topic);
        updatesService.handleUpdate(linkUpdate);
    }

    @DltHandler
    public void handleDltUpdates(LinkUpdateRequest linkUpdate, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("DLT received : {} from {}", linkUpdate, topic);
    }
}
