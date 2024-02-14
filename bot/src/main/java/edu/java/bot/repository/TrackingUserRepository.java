package edu.java.bot.repository;

import edu.java.bot.model.TrackingUser;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class TrackingUserRepository {
    private final Map<Long, TrackingUser> chats = new HashMap<>();

    public TrackingUser getTrackingUserByChatId(Long chatId) {
        if (!chats.containsKey(chatId)) {
            addTrackingUser(chatId);
        }
        return chats.get(chatId);
    }

    public void addTrackingUser(Long chatId) {
        if (!chats.containsKey(chatId)) {
            TrackingUser newTrackingUser = new TrackingUser(chatId);
            chats.put(chatId, newTrackingUser);
        }
    }
}
