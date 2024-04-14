package edu.java.bot.kafka;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.service.UpdatesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMessageConsumer {

    @Autowired
    private UpdatesService updatesService;

    @KafkaListener(topics = "${spring.kafka.topic}",
                   groupId = "${spring.kafka.consumer.group-id}",
                   properties = {"spring.json.value.default.type=edu.java.bot.dto.LinkUpdateRequest"})
    public void consume(List<LinkUpdateRequest> linkUpdates) {
        linkUpdates.forEach(updatesService::handleUpdate);
        log.info("Received updates batch size: {}", linkUpdates.size());
    }
}
