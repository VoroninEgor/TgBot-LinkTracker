package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractCommand {
    private final String command;
    private final String description;

    public boolean supports(String providedCommand) {
        return command.equals(providedCommand);
    }

    public BotCommand toApiCommand() {
        return new BotCommand(command, description);
    }

    public abstract SendMessage handle(Update update);
}
