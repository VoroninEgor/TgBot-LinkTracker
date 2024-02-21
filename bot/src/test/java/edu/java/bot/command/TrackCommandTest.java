package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingLinksRepository;
import edu.java.bot.utill.URLChecker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class TrackCommandTest extends AbstractCommandTest {

    @Autowired
    TrackCommand trackCommand;

    @Autowired
    TrackingLinksRepository trackingLinksRepository;

    @BeforeAll
    static void setUp() {
        mockStatic(URLChecker.class);
    }

    @Test
    void handleCorrectUrl_shouldReturnSuccessResponse() {
        String validUrl = "http://github.com";
        String commandMessage = "/track http://github.com";
        when(URLChecker.isValid(validUrl)).thenReturn(true);
        Update update = getMockUpdate(5L, commandMessage);

        SendMessage sendMessage = trackCommand.handle(update);

        assertEquals("Successfully added!", sendMessage.getParameters().get("text"));
    }

    @Test
    void handleCorrectUrl_shouldAddLink() {
        Long id = 5L;
        String validUrl = "http://github.com";
        String commandMessage = "/track http://github.com";
        when(URLChecker.isValid(validUrl)).thenReturn(true);
        Update update = getMockUpdate(id, commandMessage);

        trackCommand.handle(update);
        Set<String> trackLinks = trackingLinksRepository.getTrackingLinksByChatId(id).getTrackLinks();

        assertTrue(trackLinks.contains(validUrl));
    }

    @Test
    void handleIncorrectUrl() {
        String invalidUrl = "http://github.com";
        String commandMessage = "/track http://github.com";
        when(URLChecker.isValid(invalidUrl)).thenReturn(false);
        Update update = getMockUpdate(5L, commandMessage);

        SendMessage sendMessage = trackCommand.handle(update);

        assertEquals(
            "Use a valid URL as a parameter in the form like '/track <url>'",
            sendMessage.getParameters().get("text")
        );
    }
}
