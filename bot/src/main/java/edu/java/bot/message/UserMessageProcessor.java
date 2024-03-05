package edu.java.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.AbstractCommand;
import edu.java.bot.utill.MessageUtils;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMessageProcessor {
    @Getter
    private final List<AbstractCommand> commands;
    private final MessageUtils messageUtils;

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
