package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.BotApplication;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingLinksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BotApplication.class)
class ListCommandTest {

    @Autowired
    ListCommand listCommand;
    @Autowired
    TrackingLinksRepository repository;

    @BeforeEach
    void setUp() {
        repository.addTrackingLinks(1L);
        repository.addTrackingLinks(2L);
        TrackingLinks withNotEmptyTrackLinks = repository.getTrackingLinksByChatId(2L);
        withNotEmptyTrackLinks.track("http://github.com");
        withNotEmptyTrackLinks.track("http://stackoverflow.com");
    }

    @Test
    public void handleEmptyTrackList() {
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(1L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        SendMessage sendMessage = listCommand.handle(update);

        assertEquals("You don't have tracking resources, use /track"
            , sendMessage.getParameters().get("text"));
    }

    @Test
    public void handleNotEmptyTrackList() {
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(2L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        SendMessage sendMessage = listCommand.handle(update);

        assertEquals("You've tracked:\n# http://github.com\n# http://stackoverflow.com\n"
            , sendMessage.getParameters().get("text"));

    }

}
