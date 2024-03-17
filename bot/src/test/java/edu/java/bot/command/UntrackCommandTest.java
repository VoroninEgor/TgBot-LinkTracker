package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperLinkClient;
import edu.java.bot.client.ScrapperLinkClientImpl;
import edu.java.bot.dto.LinkResponse;
import edu.java.bot.utill.MessageUtils;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UntrackCommandTest extends AbstractCommandTest {

    @Autowired
    MessageUtils messageUtils;
    UntrackCommand untrackCommand;
    ScrapperLinkClient scrapperLinkClient;

    @BeforeEach
    void setUp() {
        scrapperLinkClient = mock(ScrapperLinkClientImpl.class);
        untrackCommand = new UntrackCommand(messageUtils, scrapperLinkClient);
    }

    @Test
    void handleCorrectUrl_shouldReturnSuccessResponse() {
        when(scrapperLinkClient.linksDelete(any(), any())).thenReturn(new LinkResponse(1L, URI.create("link")));
        String commandMessage = "/track http://github.com";
        Update update = getMockUpdate(2L, commandMessage);

        SendMessage sendMessage = untrackCommand.handle(update);

        assertEquals("Successfully stop tracking", sendMessage.getParameters().get("text"));
    }

    @Test
    void handleIncorrectUrl() {
        when(scrapperLinkClient.linksDelete(any(), any())).thenReturn(new LinkResponse(null, URI.create("link")));

        String commandMessage = "/untrack http://invalidurl";
        Update update = getMockUpdate(2L, commandMessage);

        SendMessage sendMessage = untrackCommand.handle(update);

        assertEquals(
            "Use a valid tracking URL as a parameter in the form like '/untrack <link>'",
            sendMessage.getParameters().get("text")
        );
    }
}
