package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingLinksRepository;
import edu.java.bot.utill.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UntrackCommand extends AbstractCommand {
    private final static String COMMAND = "/untrack";
    private final static String DESCRIPTION = "Stop tracking";
    private final static String MESSAGE = "Use a valid tracking URL as a parameter in the form like '/untrack <url>'";
    private final TrackingLinksRepository trackingLinksRepository;
    private final MessageUtils messageUtils;

    public UntrackCommand(TrackingLinksRepository trackingLinksRepository, MessageUtils messageUtils) {
        super(COMMAND, DESCRIPTION);
        this.trackingLinksRepository = trackingLinksRepository;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage handle(Update update) {
        TrackingLinks trackingUser = trackingLinksRepository.getTrackingLinksByChatId(update.message().chat().id());
        Long chatId = update.message().chat().id();

        String url = messageUtils.parseUrlFromText(update.message().text());
        if (trackingUser.getTrackLinks().contains(url)) {
            trackingUser.untrack(url);
            return new SendMessage(chatId, "Successfully stop tracking");
        }
        log.warn("invalid url was sent, untrack command");
        return new SendMessage(chatId, MESSAGE);
    }
}
