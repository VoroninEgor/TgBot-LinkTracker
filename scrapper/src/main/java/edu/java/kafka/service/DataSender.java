package edu.java.kafka.service;

import edu.java.dto.link.LinkUpdateRequest;

public interface DataSender {

    void send(LinkUpdateRequest message);
}
