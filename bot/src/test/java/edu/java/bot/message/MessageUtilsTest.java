package edu.java.bot.message;

import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.model.TrackingLinks;
import edu.java.bot.repository.TrackingLinksRepository;
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
        TrackingLinks trackingLinks = mock(TrackingLinks.class);
        when(trackingLinks.getTrackLinks()).thenReturn(Set.of());
        TrackingLinksRepository trackingLinksRepository = mock(TrackingLinksRepository.class);
        when(trackingLinksRepository.getTrackingLinksByChatId(5L)).thenReturn(trackingLinks);

        MessageUtils messageUtils = new MessageUtils(trackingLinksRepository);

        String message = messageUtils.getTrackLinks(5L);

        assertEquals("You don't have tracking resources, use /track", message);
    }

    @Test
    public void getNotEmptyTrackList() {
        TrackingLinks trackingLinks = mock(TrackingLinks.class);
        when(trackingLinks.getTrackLinks()).thenReturn(Set.of("Track1"));
        TrackingLinksRepository trackingLinksRepository = mock(TrackingLinksRepository.class);
        when(trackingLinksRepository.getTrackingLinksByChatId(5L)).thenReturn(trackingLinks);

        MessageUtils messageUtils = new MessageUtils(trackingLinksRepository);

        String message = messageUtils.getTrackLinks(5L);

        assertEquals("You've tracked:\n# Track1\n", message);
    }

    @Test
    public void getCommandsDescription() {
        MessageUtils mockMessageUtils = mock(MessageUtils.class);
        TrackingLinksRepository trackingLinksRepository = mock(TrackingLinksRepository.class);

        StartCommand startCommand = new StartCommand();
        ListCommand listCommand = new ListCommand(mockMessageUtils);

        MessageUtils messageUtils = new MessageUtils(trackingLinksRepository);

        assertEquals(
            "Available commands:\n/list | Write tracking resources\n/list | Write tracking resources"
            , messageUtils.getCommandsDescription(List.of(startCommand, listCommand, listCommand)));
    }

}
