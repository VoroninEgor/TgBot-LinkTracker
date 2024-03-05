package edu.java.bot.utill;

import edu.java.bot.command.AbstractCommand;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingLinksRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageUtils {
    private final TrackingLinksRepository trackingLinksRepository;

    public String getCommandsDescription(List<AbstractCommand> commands) {
        return "Available commands:\n"
            + commands.stream()
            .filter(command -> !command.getCommand().equals("/start"))
            .map(command -> command.getCommand() + " | " + command.getDescription())
            .collect(Collectors.joining("\n"));
    }

    public String getTrackLinks(Long chatId) {
        TrackingLinks trackingLinks = trackingLinksRepository.getTrackingLinksByChatId(chatId);
        Set<String> trackList = trackingLinks.getTrackLinks();
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
        return text.substring(0, indexOfSpace != -1 ? indexOfSpace : text.length());
    }

    public String parseUrlFromText(String text) {
        if (text == null) {
            return null;
        }

        int indexOfSpace = text.indexOf(" ");
        return indexOfSpace != -1 ? text.substring(indexOfSpace + 1) : "";
    }
}
