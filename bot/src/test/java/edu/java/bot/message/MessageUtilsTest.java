package edu.java.bot.message;

import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingUserRepository;
import edu.java.bot.utill.MessageUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Set;

class MessageUtilsTest {

    @Test
    public void getEmptyTrackList() {
        TrackingLinks trackingUser = mock(TrackingLinks.class);
        when(trackingUser.getTrackLinks()).thenReturn(Set.of());
        TrackingUserRepository trackingUserRepository = mock(TrackingUserRepository.class);
        when(trackingUserRepository.getTrackingUserByChatId(5L)).thenReturn(trackingUser);

        MessageUtils messageUtils = new MessageUtils(trackingUserRepository);

        String message = messageUtils.getTrackLinks(5L);

        assertEquals("You don't have tracking resources, use /track", message);
    }

    @Test
    public void getNotEmptyTrackList() {
        TrackingLinks trackingUser = mock(TrackingLinks.class);
        when(trackingUser.getTrackLinks()).thenReturn(Set.of("Track1"));
        TrackingUserRepository trackingUserRepository = mock(TrackingUserRepository.class);
        when(trackingUserRepository.getTrackingUserByChatId(5L)).thenReturn(trackingUser);

        MessageUtils messageUtils = new MessageUtils(trackingUserRepository);

        String message = messageUtils.getTrackLinks(5L);

        assertEquals("You've tracked:\n# Track1\n", message);
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
