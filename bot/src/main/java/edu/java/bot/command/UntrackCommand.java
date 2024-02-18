package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.message.MessageUtils;
import edu.java.bot.model.TrackingUser;
import edu.java.bot.repository.TrackingUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UntrackCommand extends AbstractCommand {
    private final static String COMMAND = "/untrack";
    private final static String DESCRIPTION = "Stop tracking";
    private final static String MESSAGE = "Use a valid URL as a parameter in the form like '/track <url>'";
    private final TrackingUserRepository trackingUserRepository;
    private final MessageUtils messageUtils;

    public UntrackCommand(TrackingUserRepository trackingUserRepository, MessageUtils messageUtils) {
        super(COMMAND, DESCRIPTION);
        this.trackingUserRepository = trackingUserRepository;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage handle(Update update) {
        TrackingUser trackingUser = trackingUserRepository.getTrackingUserByChatId(update.message().chat().id());
        Long chatId = update.message().chat().id();

        String url = messageUtils.parseUrlFromText(update.message().text());
        if (trackingUser.getTrackList().contains(url)) {
            trackingUser.untrack(url);
            return new SendMessage(chatId, "Successfully stop tracking");
        }
        log.warn("invalid url was sent, untrack command");
        return new SendMessage(chatId, MESSAGE);
    }
}
