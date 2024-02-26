package edu.java.controller;

import edu.java.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.notes.api.LinksApi;
import ru.tinkoff.notes.model.AddLinkRequest;
import ru.tinkoff.notes.model.LinkResponse;
import ru.tinkoff.notes.model.ListLinksResponse;
import ru.tinkoff.notes.model.RemoveLinkRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LinkController implements LinksApi {

    private final LinkService linksService;

    @Override
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        log.info("/links DELETE endpoint");
        LinkResponse removedLinkResponse = linksService.removeLink(tgChatId, removeLinkRequest);
        return ResponseEntity.ok(removedLinkResponse);
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId) {
        log.info("/links GET endpoint");
        ListLinksResponse linksResponse = linksService.getLinks(tgChatId);
        return ResponseEntity.ok(linksResponse);
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest addLinkRequest) {
        log.info("/links POST endpoint");
        LinkResponse addedLinkResponse = linksService.addLink(tgChatId, addLinkRequest);
        return ResponseEntity.ok(addedLinkResponse);
    }
}
