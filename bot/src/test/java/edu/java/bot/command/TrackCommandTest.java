package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.message.MessageUtils;
import edu.java.bot.model.TrackingUser;
import edu.java.bot.repository.TrackingUserRepository;
import edu.java.bot.utill.URLChecker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class TrackCommandTest {
    @BeforeAll
    static void setUp() {
        mockStatic(URLChecker.class);
    }
    @Test
    void handleCorrectUrl() {
        MessageUtils messageUtils = mock(MessageUtils.class);
        TrackingUserRepository trackingUserRepository = mock(TrackingUserRepository.class);
        TrackingUser trackingUser = mock(TrackingUser.class);

        String url = "Valid url";

        when(trackingUserRepository.getTrackingUserByChatId(5L)).thenReturn(trackingUser);
        when(messageUtils.parseUrlFromText(url)).thenReturn(url);
        when(URLChecker.isValid(url)).thenReturn(true);

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.text()).thenReturn(url);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        TrackCommand trackCommand = new TrackCommand(trackingUserRepository, messageUtils);

        assertEquals("Successfully added!"
            , trackCommand.handle(update).getParameters().get("text"));

    }

    @Test
    void handleIncorrectUrl() {
        MessageUtils messageUtils = mock(MessageUtils.class);
        TrackingUserRepository trackingUserRepository = mock(TrackingUserRepository.class);
        TrackingUser trackingUser = mock(TrackingUser.class);
        String url = "Valid url";

        when(trackingUserRepository.getTrackingUserByChatId(5L)).thenReturn(trackingUser);
        when(messageUtils.parseUrlFromText(url)).thenReturn(url);
        when(URLChecker.isValid(url)).thenReturn(false);

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.text()).thenReturn(url);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        TrackCommand trackCommand = new TrackCommand(trackingUserRepository, messageUtils);

        assertEquals("Use a valid URL as a parameter in the form like '/track <url>'"
            , trackCommand.handle(update).getParameters().get("text"));

    }
}
