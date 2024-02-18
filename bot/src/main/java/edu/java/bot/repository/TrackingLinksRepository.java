package edu.java.bot.repository;

import edu.java.bot.model.TrackingLinks;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class TrackingLinksRepository {
    private final Map<Long, TrackingLinks> chats = new HashMap<>();

    public TrackingLinks getTrackingLinksByChatId(Long chatId) {
        if (!chats.containsKey(chatId)) {
            addTrackingLinks(chatId);
        }
        return chats.get(chatId);
    }

    public void addTrackingLinks(Long chatId) {
        if (!chats.containsKey(chatId)) {
            TrackingLinks newTrackingLinks = new TrackingLinks();
            chats.put(chatId, newTrackingLinks);
        }
    }
}
