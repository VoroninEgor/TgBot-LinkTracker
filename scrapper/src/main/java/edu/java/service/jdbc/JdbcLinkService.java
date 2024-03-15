package edu.java.service.jdbc;

import edu.java.dao.JdbcLinkDao;
import edu.java.dao.JdbcTgChatLinksDao;
import edu.java.dto.AddLinkRequest;
import edu.java.dto.RemoveLinkRequest;
import edu.java.dto.link.LinkResponse;
import edu.java.dto.link.LinkUpdateResponse;
import edu.java.dto.link.ListLinksResponse;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class JdbcLinkService implements LinkService {
    private final JdbcLinkDao linkRepository;
    private final JdbcTgChatLinksDao chatLinksRepository;
    private final JdbcTgChatService tgChatService;

    @Transactional
    @Override
    public LinkResponse removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        URI url = removeLinkRequest.link();
        Long linkId = linkRepository.findIdByUrl(url);
        chatLinksRepository.remove(tgChatId, linkId);
        return LinkResponse.builder()
            .id(linkId)
            .url(url)
            .build();
    }

    @Override
    public ListLinksResponse getLinks(Long tgChatId) {
        List<LinkResponse> links = linkRepository.findAllByTgChatId(tgChatId);
        return new ListLinksResponse(links, links.size());
    }

    @Transactional
    @Override
    public LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        URI url = addLinkRequest.link();
        Long linkId = null;
        try {
            linkId = linkRepository.findIdByUrl(url);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Link {} was not added yet", url);
        }
        if (linkId == null) {
            linkId = linkRepository.save(url.toString());
        }
        chatLinksRepository.save(tgChatId, linkId);

        return LinkResponse.builder()
            .id(linkId)
            .url(url)
            .build();
    }

    @Override
    public List<LinkUpdateResponse> findLinksToCheckForUpdates(Long forceCheckDelay) {
        log.info("Find links to check for updates...");
        return linkRepository.findLinksToCheckForUpdates(forceCheckDelay);
    }

    @Transactional
    @Override
    public void updateLink(URI link, OffsetDateTime updatedAt) {
        log.info("Update link " + link + "with new updatedAt: " + updatedAt);
        linkRepository.update(link.toString(), updatedAt);
    }

    @Transactional
    @Override
    public void remove(URI link) {
        linkRepository.remove(link.toString());
    }
}
