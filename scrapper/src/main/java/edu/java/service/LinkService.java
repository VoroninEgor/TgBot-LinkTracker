package edu.java.service;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import org.springframework.stereotype.Service;


@Service
public class LinkService {
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return null;
    }

    public ListLinksResponse getLinks(Long tgChatId) {
        return null;
    }

    public LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        //        throw new LinkAlreadyAddedException(); //TODO
        return null;
    }
}
