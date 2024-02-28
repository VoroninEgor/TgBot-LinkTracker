package edu.java.controller.link;

import edu.java.dto.AddLinkRequest;
import edu.java.dto.LinkResponse;
import edu.java.dto.ListLinksResponse;
import edu.java.dto.RemoveLinkRequest;
import edu.java.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/links")
public class LinkController implements LinkApi {

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
