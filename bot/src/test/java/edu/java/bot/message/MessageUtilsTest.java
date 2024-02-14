package edu.java.bot.message;

import edu.java.bot.command.AbstractCommand;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.model.TrackingUser;
import edu.java.bot.repository.TrackingUserRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;

class MessageUtilsTest {

    @Test
    public void getEmptyTrackList() {
        TrackingUser trackingUser = mock(TrackingUser.class);
        when(trackingUser.getTrackList()).thenReturn(List.of());
        TrackingUserRepository trackingUserRepository = mock(TrackingUserRepository.class);
        when(trackingUserRepository.getTrackingUserByChatId(5L)).thenReturn(trackingUser);

        MessageUtils messageUtils = new MessageUtils(trackingUserRepository);

        String message = messageUtils.getTrackList(5L);

        assertEquals("You don't have tracking resources, use /track", message);
    }

    @Test
    public void getNotEmptyTrackList() {
        TrackingUser trackingUser = mock(TrackingUser.class);
        when(trackingUser.getTrackList()).thenReturn(List.of("Track1", "Track2"));
        TrackingUserRepository trackingUserRepository = mock(TrackingUserRepository.class);
        when(trackingUserRepository.getTrackingUserByChatId(5L)).thenReturn(trackingUser);

        MessageUtils messageUtils = new MessageUtils(trackingUserRepository);

        String message = messageUtils.getTrackList(5L);

        assertEquals("You've tracked:\n# Track1\n# Track2\n", message);
    }

    @Test
    public void getCommandsDescription() {
        MessageUtils mockMessageUtils = mock(MessageUtils.class);
        TrackingUserRepository trackingUserRepository = mock(TrackingUserRepository.class);

        StartCommand startCommand = new StartCommand();
        ListCommand listCommand = new ListCommand(mockMessageUtils);

        MessageUtils messageUtils = new MessageUtils(trackingUserRepository);

        assertEquals(
            "Available commands:\n/list | Write tracking resources\n/list | Write tracking resources"
            , messageUtils.getCommandsDescription(List.of(startCommand, listCommand, listCommand)));
    }

}
