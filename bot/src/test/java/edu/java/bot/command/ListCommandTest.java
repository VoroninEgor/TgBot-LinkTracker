package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.utill.MessageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCommandTest extends AbstractCommandTest {

    ListCommand listCommand;
    MessageUtils messageUtils;

    @BeforeEach
    void setUp() {
        messageUtils = mock(MessageUtils.class);
        listCommand = new ListCommand(messageUtils);
    }

    @Test
    public void handleEmptyTrackList() {
        when(messageUtils.getTrackLinks(any()))
            .thenReturn("You don't have tracking resources, use /track");
        Update update = getMockUpdate(1L, "text");

        SendMessage sendMessage = listCommand.handle(update);

        assertEquals("You don't have tracking resources, use /track"
            , sendMessage.getParameters().get("text"));
    }

    @Test
    public void handleNotEmptyTrackList() {
        when(messageUtils.getTrackLinks(any()))
            .thenReturn("You've tracked:\n# http://github.com\n# http://stackoverflow.com\n");

        Update update = getMockUpdate(2L, "text");

        SendMessage sendMessage = listCommand.handle(update);

        assertEquals("You've tracked:\n# http://github.com\n# http://stackoverflow.com\n"
            , sendMessage.getParameters().get("text"));
    }

}
