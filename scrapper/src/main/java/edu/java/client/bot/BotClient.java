package edu.java.client.bot;

import edu.java.dto.LinkUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface BotClient {
    ResponseEntity<Void> updatesPost(LinkUpdateRequest linkUpdate);
}
