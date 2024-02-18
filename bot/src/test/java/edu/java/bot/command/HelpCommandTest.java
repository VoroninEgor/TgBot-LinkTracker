package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.BotApplication;
import edu.java.bot.utill.MessageUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BotApplication.class)
class HelpCommandTest {

    @Autowired
    List<AbstractCommand> commandList;
    @Autowired
    HelpCommand helpCommand;
    @Autowired
    MessageUtils messageUtils;

    @BeforeEach
    void setUp() {
        commandList.remove(helpCommand);
        commandList.add(helpCommand);
    }

    @Test
    void handle() {
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        SendMessage sendMessage = helpCommand.handle(update);

        assertEquals(messageUtils.getCommandsDescription(commandList), sendMessage.getParameters().get("text"));
    }
}
