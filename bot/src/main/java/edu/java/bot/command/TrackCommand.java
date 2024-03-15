package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperLinkClient;
import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.utill.MessageUtils;
import edu.java.bot.utill.URLChecker;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrackCommand extends AbstractCommand {
    private final static String COMMAND = "/track";
    private final static String DESCRIPTION = "Start tracking";

    private final static String MESSAGE = "Use a valid URL as a parameter in the form like '/track <link>'";
    private final MessageUtils messageUtils;
    private final ScrapperLinkClient scrapperLinkClient;

    public TrackCommand(MessageUtils messageUtils, ScrapperLinkClient scrapperLinkClient) {
        super(COMMAND, DESCRIPTION);
        this.messageUtils = messageUtils;
        this.scrapperLinkClient = scrapperLinkClient;
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("TrackCommand handling...");
        Long chatId = update.message().chat().id();
        String url = messageUtils.parseUrlFromText(update.message().text());
        if (URLChecker.isValid(url)) {
            scrapperLinkClient.linksPost(chatId, new AddLinkRequest(URI.create(url)));
            return new SendMessage(chatId, "Successfully added!");
        }
        log.warn("invalid link was sent, track command");
        return new SendMessage(chatId, MESSAGE);
    }
}
