package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.utill.MessageUtils;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingUserRepository;
import edu.java.bot.utill.URLChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrackCommand extends AbstractCommand {
    private final static String COMMAND = "/track";
    private final static String DESCRIPTION = "Start tracking";
    private final static String MESSAGE = "Use a valid URL as a parameter in the form like '/track <url>'";
    private final TrackingUserRepository trackingUserRepository;
    private final MessageUtils messageUtils;

    public TrackCommand(TrackingUserRepository trackingUserRepository, MessageUtils messageUtils) {
        super(COMMAND, DESCRIPTION);
        this.trackingUserRepository = trackingUserRepository;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        TrackingLinks trackingUser = trackingUserRepository.getTrackingUserByChatId(chatId);
        String url = messageUtils.parseUrlFromText(update.message().text());
        if (URLChecker.isValid(url)) {
            trackingUser.track(url);
            return new SendMessage(chatId, "Successfully added!");
        }
        log.warn("invalid url was sent, track command");
        return new SendMessage(chatId, MESSAGE);
    }
}
