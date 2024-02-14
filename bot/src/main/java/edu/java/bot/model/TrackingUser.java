package edu.java.bot.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingUser {
    private final Long chatId;
    private final List<String> trackList = new ArrayList<>();
    private String activeCommand;

    public TrackingUser(Long chatId) {
        this.chatId = chatId;
        setWithoutActiveCommand();
    }

    public void track(String url) {
        if (!trackList.contains(url)) {
            trackList.add(url);
        }
    }

    public void untrack(String url) {
        trackList.remove(url);
    }

    public void setWithoutActiveCommand() {
        activeCommand = "NONE";
    }

}
