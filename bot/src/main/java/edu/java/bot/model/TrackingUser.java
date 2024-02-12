package edu.java.bot.model;

import edu.java.bot.utill.command.Command;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TrackingUser {
    private final Long chatId;
    private final List<String> trackList = new ArrayList<>();
    private Command activeCommand = Command.UNKNOWN;


    public TrackingUser(Long chatId) {
        this.chatId = chatId;
    }

    public void track(String url) {
        if (!trackList.contains(url)) {
            trackList.add(url);
        }
    }

    public void untrack(String url) {
        trackList.remove(url);
    }

    public void setActiveCommand(Command command) {
        if (command.isActive()) {
            activeCommand = command;
        }
    }

    public void finishCommand() {
        activeCommand = Command.UNKNOWN;
    }
}
