package edu.java.service.jpa;

import edu.java.dto.link.AddLinkRequest;
import edu.java.dto.link.LinkResponse;
import edu.java.dto.link.LinkUpdateResponse;
import edu.java.dto.link.ListLinksResponse;
import edu.java.dto.link.RemoveLinkRequest;
import edu.java.entity.Link;
import edu.java.entity.TgChat;
import edu.java.entity.TgChatLink;
import edu.java.repository.LinkRepo;
import edu.java.repository.TgChatLinkRepo;
import edu.java.repository.TgChatRepo;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class JpaLinkService implements LinkService {

    private final LinkRepo linkRepo;
    private final TgChatRepo tgChatRepo;
    private final TgChatLinkRepo tgChatLinkRepo;

    @Transactional
    @Override
    public LinkResponse untrackLinkForUser(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        TgChat tgChat = tgChatRepo.findById(tgChatId).orElseThrow();
        Link link = linkRepo.findLinkByUrl(removeLinkRequest.link().toString()).orElseThrow();
        Long tgChatLinksId = tgChat.getTgChatLinks().stream()
            .filter(chatLink -> chatLink.getLink().getUrl().equals(link.getUrl()))
            .findFirst()
            .map(TgChatLink::getId)
            .get();
        tgChatLinkRepo.deleteById(tgChatLinksId);
        return LinkResponse.builder()
            .id(link.getId())
            .link(URI.create(link.getUrl()))
            .build();
    }

    @Override
    public ListLinksResponse getLinksByTgChatId(Long tgChatId) {
        TgChat tgChat = tgChatRepo.findById(tgChatId).orElseThrow();

        List<LinkResponse> linkResponses = tgChat.getTgChatLinks().stream()
            .map(TgChatLink::getLink)
            .map(l -> new LinkResponse(l.getId(), URI.create(l.getUrl())))
            .toList();

        return new ListLinksResponse(linkResponses, linkResponses.size());
    }

    @Transactional
    @Override
    public LinkResponse trackLinkForUser(Long tgChatId, AddLinkRequest addLinkRequest) {
        String url = addLinkRequest.link().toString();
        TgChat tgChat = tgChatRepo.findById(tgChatId).orElseThrow();

        Optional<Link> optionalLinkInChat = tgChat.getTgChatLinks().stream()
            .map(TgChatLink::getLink)
            .filter(v -> v.getUrl().equals(url))
            .findFirst();

        if (optionalLinkInChat.isPresent()) {
            Link link = optionalLinkInChat.get();
            return LinkResponse.builder()
                .id(link.getId())
                .link(URI.create(link.getUrl()))
                .build();
        }

        Optional<Link> optionalLink = linkRepo.findLinkByUrl(url);
        Link link;

        if (optionalLink.isEmpty()) {
            log.info("link {} not exist yet", url);
            link = linkRepo.save(Link.builder().url(url).lastCheck(OffsetDateTime.now()).build());
        } else {
            log.info("link {} already exist", url);
            link = optionalLink.get();
        }

        TgChatLink tgChatLink = new TgChatLink();
        tgChatLink.setChat(tgChat);
        tgChatLink.setLink(link);
        tgChatLinkRepo.save(tgChatLink);

        return LinkResponse.builder()
            .id(link.getId())
            .link(URI.create(link.getUrl()))
            .build();
    }

    @Transactional
    @Override
    public List<LinkUpdateResponse> findLinksToCheckForUpdates(Long forceCheckDelay) {
        List<Link> allByLastCheckBefore =
            linkRepo.getAllByLastCheckBefore(OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(forceCheckDelay));
        return allByLastCheckBefore.stream()
            .map(l -> new LinkUpdateResponse(l.getId(), URI.create(l.getUrl()), l.getLastCheck()))
            .toList();
    }

    @Transactional
    @Override
    public void updateLink(URI link, OffsetDateTime lastCheck) {
        Link linkToUpdate = linkRepo.findLinkByUrl(link.toString()).orElseThrow();
        linkToUpdate.setLastCheck(lastCheck);
        linkRepo.save(linkToUpdate);
    }

    @Transactional
    @Override
    public void remove(URI link) {
        linkRepo.deleteByUrl(link.toString());
    }
}
