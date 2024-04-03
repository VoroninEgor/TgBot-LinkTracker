package edu.java.bot.message;

import edu.java.bot.client.ScrapperLinkClient;
import edu.java.bot.client.ScrapperLinkClientImpl;
import edu.java.bot.client.ScrapperTgChatClient;
import edu.java.bot.client.ScrapperTgChatClientImpl;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.dto.LinkResponse;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.utill.MessageUtils;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageUtilsTest {

    ScrapperLinkClient scrapperLinkClient = mock(ScrapperLinkClientImpl.class);
    ScrapperTgChatClient scrapperTgChatClient = mock(ScrapperTgChatClientImpl.class);
    MessageUtils messageUtils = new MessageUtils(scrapperLinkClient);
    StartCommand startCommand = new StartCommand(scrapperTgChatClient);
    ListCommand listCommand = new ListCommand(messageUtils);

    @Test
    public void getEmptyTrackList() {
        when(scrapperLinkClient.getLinksByChatId(any())).thenReturn(new ListLinksResponse(List.of(), 0));

        String message = messageUtils.getTrackLinks(5L);

        assertEquals("You don't have tracking resources, use /track", message);
    }

    @Test
    public void getNotEmptyTrackList() {
        when(scrapperLinkClient.getLinksByChatId(any()))
            .thenReturn(new ListLinksResponse(List.of(new LinkResponse(1L, URI.create("Track1"))), 1));

        String message = messageUtils.getTrackLinks(5L);

        assertEquals("You've tracked:\n# Track1\n", message);
    }

    @Test
    public void getCommandsDescription() {
        assertEquals(
            "Available commands:\n/list | Write tracking resources\n/list | Write tracking resources"
            , messageUtils.getCommandsDescription(List.of(startCommand, listCommand, listCommand)));
    }

}
