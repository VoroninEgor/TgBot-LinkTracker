package edu.java.bot.utill.command;

import lombok.Getter;

@Getter
public enum Command {
    START("/start", false),
    HELP("/help", false),
    TRACK("/track", true),
    UNTRACK("/untrack", true),
    LIST("/list", false),
    UNKNOWN("", false);
    private final String prefix;
    private final boolean isActive;

    Command(String prefix, boolean isActive) {
        this.prefix = prefix;
        this.isActive = isActive;
    }
}
