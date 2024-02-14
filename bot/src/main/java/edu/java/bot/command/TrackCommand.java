package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.message.MessageUtils;
import edu.java.bot.model.TrackingUser;
import edu.java.bot.repository.TrackingUserRepository;
import edu.java.bot.utill.URLChecker;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand extends AbstractCommand {
    private final static String COMMAND = "/track";
    private final static String DESCRIPTION = "Start tracking";
    private final static String MESSAGE = "Send me the URL of the resource you want to track";
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
        TrackingUser trackingUser = trackingUserRepository.getTrackingUserByChatId(chatId);
        String url = messageUtils.parseUrlFromText(update.message().text());
        if (URLChecker.isValid(url)) {
            trackingUser.track(url);
            return new SendMessage(chatId, "Successfully added!");
        }
        return new SendMessage(chatId, MESSAGE);
    }
}
