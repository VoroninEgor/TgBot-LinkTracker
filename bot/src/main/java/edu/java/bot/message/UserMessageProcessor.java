package edu.java.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.AbstractCommand;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserMessageProcessor {
    @Getter
    private final List<AbstractCommand> commands;
    private final MessageUtils messageUtils;

    public UserMessageProcessor(List<AbstractCommand> commands, MessageUtils messageUtils) {
        this.commands = commands;
        this.messageUtils = messageUtils;
    }

    public SendMessage process(Update update) {
        String text = update.message().text();
        String commandFromText = messageUtils.parseCommandFromText(text);

        for (AbstractCommand command : commands) {
            if (command.supports(commandFromText)) {
                return command.handle(update);
            }
        }

        return new SendMessage(update.message().chat().id(), "This command is incorrect. "
            + "Use /help to find out the available commands");
    }

}
