package edu.java.service;

import edu.java.dto.link.AddLinkRequest;
import edu.java.dto.link.LinkResponse;
import edu.java.dto.link.LinkUpdateResponse;
import edu.java.dto.link.ListLinksResponse;
import edu.java.dto.link.RemoveLinkRequest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    LinkResponse removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    ListLinksResponse getLinks(Long tgChatId);

    LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest);

    List<LinkUpdateResponse> findLinksToCheckForUpdates(Long forceCheckDelay);

    void updateLink(URI link, OffsetDateTime lastCheck);

    void remove(URI link);
}
