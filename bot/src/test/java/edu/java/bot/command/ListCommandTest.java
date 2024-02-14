package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.message.MessageUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCommandTest {

    @Test
    public void handleEmptyTrackList() {
        MessageUtils messageUtils = mock(MessageUtils.class);
        when(messageUtils.getTrackList(5L)).thenReturn("You don't have tracking resources, use /track");

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        ListCommand listCommand = new ListCommand(messageUtils);

        assertEquals("You don't have tracking resources, use /track"
            , listCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void handleNotEmptyTrackList() {
        MessageUtils messageUtils = mock(MessageUtils.class);
        when(messageUtils.getTrackList(5L)).thenReturn("You've tracked:\n# Track1\n# Track2\n");

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        ListCommand listCommand = new ListCommand(messageUtils);

        assertEquals("You've tracked:\n# Track1\n# Track2\n"
            , listCommand.handle(update).getParameters().get("text"));
    }

}
