package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.message.MessageUtils;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends AbstractCommand {
    private final static String COMMAND = "/list";
    private final static String DESCRIPTION = "Write tracking resources";
    private final MessageUtils messageUtils;

    public ListCommand(MessageUtils messageUtils) {
        super(COMMAND, DESCRIPTION);
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String message = messageUtils.getTrackList(chatId);
        return new SendMessage(chatId, message);
    }
}
