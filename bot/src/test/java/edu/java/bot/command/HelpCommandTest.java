package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.utill.MessageUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpCommandTest extends AbstractCommandTest {

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
        Update update = getMockUpdate(5L, "text");

        SendMessage sendMessage = helpCommand.handle(update);

        assertEquals(messageUtils.getCommandsDescription(commandList), sendMessage.getParameters().get("text"));
    }
}
