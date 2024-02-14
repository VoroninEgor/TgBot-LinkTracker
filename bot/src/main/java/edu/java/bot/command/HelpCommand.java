package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.message.MessageUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelpCommand extends AbstractCommand {
    private final static String COMMAND = "/help";
    private final static String DESCRIPTION = "Write available commands";
    private final String message;

    public HelpCommand(List<AbstractCommand> commands, MessageUtils messageUtils) {
        super(COMMAND, DESCRIPTION);
        commands.add(this);
        message = messageUtils.getCommandsDescription(commands);
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("HelpCommand handling...");
        return new SendMessage(update.message().chat().id(), message);
    }
}
