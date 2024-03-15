package edu.java.service;

import java.net.URI;
import java.util.List;

public interface TgChatService {
    void remove(Long id);

    void save(Long id);

    List<Long> fetchTgChatsIdByLink(URI link);
}
