package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperLinkClient;
import edu.java.bot.client.ScrapperLinkClientImpl;
import edu.java.bot.utill.MessageUtils;
import edu.java.bot.utill.URLChecker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class TrackCommandTest extends AbstractCommandTest {
    @Autowired
    MessageUtils messageUtils;
    TrackCommand trackCommand;
    ScrapperLinkClient scrapperLinkClient;

    @BeforeEach
    void setUpBeforeEach() {
        scrapperLinkClient = mock(ScrapperLinkClientImpl.class);
        when(scrapperLinkClient.linksPost(any(), any())).thenReturn(any());

        trackCommand = new TrackCommand(messageUtils, scrapperLinkClient);
    }

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
    void handleIncorrectUrl() {
        String invalidUrl = "http://github.com";
        String commandMessage = "/track http://github.com";
        when(URLChecker.isValid(invalidUrl)).thenReturn(false);
        Update update = getMockUpdate(5L, commandMessage);

        SendMessage sendMessage = trackCommand.handle(update);

        assertEquals(
            "Use a valid URL as a parameter in the form like '/track <link>'",
            sendMessage.getParameters().get("text")
        );
    }
}
