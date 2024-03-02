package edu.java.bot.client;

import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.LinkResponse;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import org.springframework.http.ResponseEntity;

public interface ScrapperLinkClient {
    ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    ResponseEntity<ListLinksResponse> linksGet(Long tgChatId);

    ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest addLinkRequest);
}
