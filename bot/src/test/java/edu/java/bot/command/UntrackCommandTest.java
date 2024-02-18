package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.BotApplication;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingLinksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BotApplication.class)
class UntrackCommandTest {

    @Autowired
    UntrackCommand untrackCommand;
    @Autowired
    TrackingLinksRepository repository;

    @Test
    void handleCorrectUrl() {
        repository.addTrackingLinks(2L);
        TrackingLinks trackingLinks = repository.getTrackingLinksByChatId(2L);
        trackingLinks.track("http://github.com");
        String commandMessage = "/track http://github.com";

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(2L);
        Message message = mock(Message.class);
        when(message.text()).thenReturn(commandMessage);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        SendMessage sendMessage = untrackCommand.handle(update);

        assertEquals("Successfully stop tracking", sendMessage.getParameters().get("text"));
    }

    @Test
    void handleIncorrectUrl() {
        String commandMessage = "/track http://invalidurl";

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(2L);
        Message message = mock(Message.class);
        when(message.text()).thenReturn(commandMessage);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        SendMessage sendMessage = untrackCommand.handle(update);

        assertEquals(
            "Use a valid tracking URL as a parameter in the form like '/untrack <url>'",
            sendMessage.getParameters().get("text")
        );
    }
}
