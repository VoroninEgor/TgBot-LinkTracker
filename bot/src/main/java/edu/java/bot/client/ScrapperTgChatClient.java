package edu.java.bot.client;

import org.springframework.http.ResponseEntity;

public interface ScrapperTgChatClient {
    ResponseEntity<Void> tgChatIdDelete(Long id);

    ResponseEntity<Void> tgChatIdPost(Long id);
}
