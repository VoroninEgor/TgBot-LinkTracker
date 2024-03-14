package edu.java.bot.client;

import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.LinkResponse;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;

public interface ScrapperLinkClient {
    LinkResponse linksDelete(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    ListLinksResponse linksGet(Long tgChatId);

    LinkResponse linksPost(Long tgChatId, AddLinkRequest addLinkRequest);
}
