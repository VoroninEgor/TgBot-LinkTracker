package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends AbstractCommand {
    private final static String COMMAND = "/start";
    private final static String DESCRIPTION = "You already starts the allowing";
    private final static String MESSAGE = "Hi! I'm happy to see you. Use /help to find out the available commands";

    public StartCommand() {
        super(COMMAND, DESCRIPTION);
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), MESSAGE);
    }
}
