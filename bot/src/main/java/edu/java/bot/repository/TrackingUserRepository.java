package edu.java.bot.repository;

import edu.java.bot.model.TrackingLinks;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class TrackingUserRepository {
    private final Map<Long, TrackingLinks> chats = new HashMap<>();

    public TrackingLinks getTrackingUserByChatId(Long chatId) {
        if (!chats.containsKey(chatId)) {
            addTrackingUser(chatId);
        }
        return chats.get(chatId);
    }

    public void addTrackingUser(Long chatId) {
        if (!chats.containsKey(chatId)) {
            TrackingLinks newTrackingLinks = new TrackingLinks();
            chats.put(chatId, newTrackingLinks);
        }
    }
}
