package edu.java.service;

import edu.java.dto.tgchatlinks.TgChatResponse;
import java.net.URI;
import java.util.List;

public interface TgChatService {
    void remove(Long id);

    void save(Long id);

    List<Long> fetchTgChatsIdByLink(URI link);

    TgChatResponse fetchById(Long id);
}
