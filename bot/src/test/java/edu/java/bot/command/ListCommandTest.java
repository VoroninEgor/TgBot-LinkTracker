package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingLinksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest extends AbstractCommandTest {

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
        Update update = getMockUpdate(1L, "text");

        SendMessage sendMessage = listCommand.handle(update);

        assertEquals("You don't have tracking resources, use /track"
            , sendMessage.getParameters().get("text"));
    }

    @Test
    public void handleNotEmptyTrackList() {
        Update update = getMockUpdate(2L, "text");

        SendMessage sendMessage = listCommand.handle(update);

        assertEquals("You've tracked:\n# http://github.com\n# http://stackoverflow.com\n"
            , sendMessage.getParameters().get("text"));
    }

}
