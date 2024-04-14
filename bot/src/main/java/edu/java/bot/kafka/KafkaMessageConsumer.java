package edu.java.bot.kafka;

import edu.java.bot.dto.LinkUpdateRequest;
import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {

    @KafkaListener(topics = "${spring.kafka.topic}",
                   groupId = "${spring.kafka.consumer.group-id}",
                   properties = {"spring.json.value.default.type=edu.java.bot.dto.LinkUpdateRequest"})
    public void consume(List<LinkUpdateRequest> message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
