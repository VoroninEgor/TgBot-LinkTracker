package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StartCommandTest extends AbstractCommandTest {

    @Autowired
    StartCommand startCommand;

    @Test
    void handle() {
        Update update = getMockUpdate(5L, "text");

        SendMessage sendMessage = startCommand.handle(update);

        assertEquals(
            "Hi! I'm happy to see you. Use /help to find out the available commands",
            sendMessage.getParameters().get("text")
        );
    }
}
