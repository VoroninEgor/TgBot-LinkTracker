package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingLinksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UntrackCommandTest extends AbstractCommandTest {

    @Autowired
    UntrackCommand untrackCommand;
    @Autowired
    TrackingLinksRepository repository;

    @Test
    void handleCorrectUrl_shouldReturnSuccessResponse() {
        repository.addTrackingLinks(2L);
        TrackingLinks trackingLinks = repository.getTrackingLinksByChatId(2L);
        trackingLinks.track("http://github.com");
        String commandMessage = "/track http://github.com";
        Update update = getMockUpdate(2L, commandMessage);

        SendMessage sendMessage = untrackCommand.handle(update);

        assertEquals("Successfully stop tracking", sendMessage.getParameters().get("text"));
    }

    @Test
    void handleCorrectUrl_shouldRemoveLink() {
        Long id = 2L;
        repository.addTrackingLinks(id);
        TrackingLinks trackingLinks = repository.getTrackingLinksByChatId(id);
        String link = "http://github.com";
        trackingLinks.track(link);
        String commandMessage = "/untrack http://github.com";
        Update update = getMockUpdate(id, commandMessage);

        untrackCommand.handle(update);
        Set<String> trackLinks = repository.getTrackingLinksByChatId(id).getTrackLinks();

        assertFalse(trackLinks.contains(link));
    }

    @Test
    void handleIncorrectUrl() {
        String commandMessage = "/untrack http://invalidurl";
        Update update = getMockUpdate(2L, commandMessage);

        SendMessage sendMessage = untrackCommand.handle(update);

        assertEquals(
            "Use a valid tracking URL as a parameter in the form like '/untrack <url>'",
            sendMessage.getParameters().get("text")
        );
    }
}
