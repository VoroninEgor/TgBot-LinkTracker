package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.model.TrackingUser;
import edu.java.bot.repository.TrackingUserRepository;
import edu.java.bot.utill.URLChecker;
import edu.java.bot.utill.command.Command;
import edu.java.bot.utill.command.CommandAnalyzer;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MyTrackingBot {
    private final TelegramBot bot;
    private final TrackingUserRepository trackingUserRepository;

    public MyTrackingBot(TrackingUserRepository trackingUserRepository, ApplicationConfig applicationConfig) {
        this.trackingUserRepository = trackingUserRepository;
        bot = new TelegramBot(applicationConfig.telegramToken());
    }

    private void start(Long chatId, TrackingUser trackingUser) {
        trackingUserRepository.addTrackingUser(chatId);
        help(chatId, trackingUser);
    }

    private void help(Long chatId, TrackingUser trackingUser) {
        bot.execute(new SendMessage(chatId, "Я могу помочь вам отслеживать обновления на многих популярных "
            + "сайтах. Вы можете управлять мной вызвав эти команды:\n\n/track - начать отслеживать сайт\n/untrack - "
        + "прекратить отслеживать сайт\n/list - вывести список отслеживаемых сайтов"));
        trackingUser.finishCommand();
    }

    private void list(Long chatId, TrackingUser trackingUser) {
        List<String> trackList = trackingUser.getTrackList();
        if (trackList.isEmpty()) {
            bot.execute(new SendMessage(chatId, "Пока что вы ничего не отслеживаете, "
                + "воспользуйтесь командой /track "));
        } else {
            bot.execute(new SendMessage(chatId, trackList.stream().collect(Collectors.joining("\n"))));
        }
        trackingUser.finishCommand();
    }

    private void track(Long chatId, TrackingUser trackingUser) {
        trackingUser.setActiveCommand(Command.TRACK);
        bot.execute(new SendMessage(chatId, "Напишите ссылку на ресурс, который хотите начать отслеживать"));
    }

    private void untrack(Long chatId, TrackingUser trackingUser) {
        trackingUser.setActiveCommand(Command.UNTRACK);
        bot.execute(new SendMessage(chatId, "Напишите ссылку на ресурс, который хотите прекратить отслеживать"));
    }

    private void unknown(Long chatId, TrackingUser trackingUser, String url) {
        Command activeCommand = trackingUser.getActiveCommand();

        switch(activeCommand) {
            case UNKNOWN -> bot.execute(new SendMessage(chatId, "Некорректная команда, попробуйте снова"));
            case TRACK -> {
                boolean isValidUrl = URLChecker.isValid(url);
                if (isValidUrl) {
                    trackingUser.track(url);
                    trackingUser.finishCommand();
                } else {
                    bot.execute(new SendMessage(chatId, "Некорректная ссылка, попробуйте снова"));
                }
            }
            case UNTRACK -> {
                trackingUser.untrack(url);
                trackingUser.finishCommand();
            }
        }
    }

    public void setUpdatesListener() {
        bot.setUpdatesListener(updates -> {
            Message lastMessage = updates.getLast().message();
            Long chatId = lastMessage.chat().id();
            String text = lastMessage.text();
            TrackingUser user = trackingUserRepository.getTrackingUserByChatId(chatId);

            Command command = CommandAnalyzer.typeOfCommand(text);
            switch (command) {
                case START -> start(chatId, user);
                case HELP -> help(chatId, user);
                case LIST -> list(chatId, user);
                case TRACK -> track(chatId, user);
                case UNTRACK -> untrack(chatId, user);
                case UNKNOWN -> unknown(chatId, user, text);
                default -> bot.execute(new SendMessage(chatId, "Ой-ой какая-то проблемка ERROR!!!"));
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

        
    }

}
