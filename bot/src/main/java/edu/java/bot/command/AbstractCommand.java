package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;

public abstract class AbstractCommand implements Command {
    private final String command;
    private final String description;

    AbstractCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean supports(String command) {
        return getCommand().equals(command);
    }

    @Override
    public BotCommand toApiCommand() {
        return new BotCommand(getCommand(), getDescription());
    }
}
