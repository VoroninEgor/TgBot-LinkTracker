package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperTgChatClient;
import edu.java.bot.client.ScrapperTgChatClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class StartCommandTest extends AbstractCommandTest {

    StartCommand startCommand;
    ScrapperTgChatClient scrapperTgChatClient;

    @BeforeEach
    void setUp() {
        scrapperTgChatClient = mock(ScrapperTgChatClientImpl.class);
        startCommand = new StartCommand(scrapperTgChatClient);
    }

    @Test
    void handle() {
        doNothing().when(scrapperTgChatClient).tgChatIdPost(any());
        Update update = getMockUpdate(5L, "text");

        SendMessage sendMessage = startCommand.handle(update);

        assertEquals(
            "Hi! I'm happy to see you. Use /help to find out the available commands",
            sendMessage.getParameters().get("text")
        );
    }
}
