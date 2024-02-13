package edu.java.bot.message;

import edu.java.bot.command.AbstractCommand;
import edu.java.bot.model.TrackingUser;
import edu.java.bot.repository.TrackingUserRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageUtils {
    private final TrackingUserRepository trackingUserRepository;

    public MessageUtils(TrackingUserRepository trackingUserRepository) {
        this.trackingUserRepository = trackingUserRepository;
    }

    public String getCommandsDescription(List<AbstractCommand> commands) {
        String message = "Available commands:\n"
            + commands.stream()
            .filter(command -> !command.getCommand().equals("/start"))
            .map(command -> command.getCommand() + " | " + command.getDescription())
            .collect(Collectors.joining("\n"));
        return message;
    }

    public String getTrackList(Long chatId) {
        TrackingUser trackingUser = trackingUserRepository.getTrackingUserByChatId(chatId);
        List<String> trackList = trackingUser.getTrackList();
        StringBuilder message = new StringBuilder();
        if (trackList.isEmpty()) {
            message.append("You don't have tracking resources, use /track");
        } else {
            message.append("You've tracked:\n");
            for (String s : trackList) {
                message.append("# ").append(s).append("\n");
            }
        }
        return message.toString();
    }

    public String parseCommandFromText(String text) {
        if (text == null) {
            return null;
        }

        int indexOfSpace = text.indexOf(" ");
        String textBeforeSpace = text.substring(0, indexOfSpace != -1 ? indexOfSpace : text.length());
        return textBeforeSpace;
    }

    public String parseUrlFromText(String text) {
        if (text == null) {
            return null;
        }

        int indexOfSpace = text.indexOf(" ");
        return indexOfSpace != -1 ? text.substring(indexOfSpace + 1) : "";
    }
}
