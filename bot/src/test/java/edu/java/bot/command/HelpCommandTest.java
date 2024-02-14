package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.message.MessageUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HelpCommandTest {

    @Test
    void handle() {
        List<AbstractCommand> commands = new ArrayList<>();
        MessageUtils messageUtils = mock(MessageUtils.class);
        when(messageUtils.getCommandsDescription(any())).thenReturn(
            "Available commands:\n/list | Write tracking resources\n/list | Write tracking resources");

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        HelpCommand helpCommand = new HelpCommand(commands, messageUtils);

        assertEquals(
            "Available commands:\n/list | Write tracking resources\n/list | Write tracking resources"
            , helpCommand.handle(update).getParameters().get("text"));

    }
}
