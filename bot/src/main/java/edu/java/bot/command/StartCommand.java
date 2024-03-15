package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperTgChatClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartCommand extends AbstractCommand {
    private final static String COMMAND = "/start";
    private final static String DESCRIPTION = "You already starts the allowing";
    private final static String MESSAGE = "Hi! I'm happy to see you. Use /help to find out the available commands";

    private final ScrapperTgChatClient scrapperTgChatClient;

    public StartCommand(ScrapperTgChatClient scrapperTgChatClient) {
        super(COMMAND, DESCRIPTION);
        this.scrapperTgChatClient = scrapperTgChatClient;
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("StartCommand handling...");
        Long tgChatId = update.message().chat().id();
        scrapperTgChatClient.tgChatIdPost(tgChatId);
        return new SendMessage(tgChatId, MESSAGE);
    }
}
