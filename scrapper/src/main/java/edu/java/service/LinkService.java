package edu.java.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.notes.model.AddLinkRequest;
import ru.tinkoff.notes.model.LinkResponse;
import ru.tinkoff.notes.model.ListLinksResponse;
import ru.tinkoff.notes.model.RemoveLinkRequest;

@Service
public class LinkService {
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return null;
    }

    public ListLinksResponse getLinks(Long tgChatId) {
        return null;
    }

    public LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return null;
    }
}
