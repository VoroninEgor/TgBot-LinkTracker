package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCommand implements Command {
    private final String command;
    private final String description;

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean supports(String providedCommand) {
        return command.equals(providedCommand);
    }

    @Override
    public BotCommand toApiCommand() {
        return new BotCommand(command, description);
    }
}
