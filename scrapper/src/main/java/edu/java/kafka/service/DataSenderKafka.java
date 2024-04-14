package edu.java.kafka.service;

import edu.java.dto.link.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class DataSenderKafka implements DataSender {

    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    private final String topic;

    public DataSenderKafka(KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void send(LinkUpdateRequest message) {
        try {
            log.info("message: {}", message);
            kafkaTemplate.send(topic, message)
                .whenComplete(
                    (result, error) -> {
                        if (error == null) {
                            log.info("message id:{} was sent, offset:{}",
                                message, result.getRecordMetadata().offset()
                            );
                        } else {
                            log.error("message id:{} was not sent", message);
                        }
                    });

        } catch (Exception e) {
            log.error("send error, message:{}", message);
        }
    }
}
