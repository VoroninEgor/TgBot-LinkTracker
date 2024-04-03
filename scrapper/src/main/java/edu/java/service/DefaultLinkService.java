package edu.java.service;

import edu.java.dao.LinkDao;
import edu.java.dao.TgChatDao;
import edu.java.dao.TgChatLinksDao;
import edu.java.dto.link.AddLinkRequest;
import edu.java.dto.link.LinkResponse;
import edu.java.dto.link.LinkUpdateResponse;
import edu.java.dto.link.ListLinksResponse;
import edu.java.dto.link.RemoveLinkRequest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DefaultLinkService implements LinkService {

    private final LinkDao linkDao;
    private final TgChatDao tgChatDao;
    private final TgChatLinksDao tgChatLinksDao;

    @Transactional
    @Override
    public LinkResponse untrackLinkForUser(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        URI url = removeLinkRequest.link();
        Long linkId = null;
        try {
            linkId = linkDao.findIdByUrl(url.toString());
            tgChatLinksDao.remove(tgChatId, linkId);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Link {} does not exist to delete", url);
        }
        return LinkResponse.builder()
            .id(linkId)
            .link(url)
            .build();
    }

    @Override
    public ListLinksResponse getLinksByTgChatId(Long tgChatId) {
        List<LinkResponse> links = linkDao.findAllByTgChatId(tgChatId);
        return new ListLinksResponse(links, links.size());
    }

    @Transactional
    @Override
    public LinkResponse trackLinkForUser(Long tgChatId, AddLinkRequest addLinkRequest) {
        URI url = addLinkRequest.link();
        Long linkId;
        try {
            linkId = linkDao.findIdByUrl(url.toString());
        } catch (EmptyResultDataAccessException e) {
            log.warn("Link {} was not added yet", url);
            linkId = linkDao.save(url.toString());
        }
        try {
            tgChatDao.fetchById(tgChatId);
        } catch (EmptyResultDataAccessException e) {
            log.warn("TgChat {} was not added yet", tgChatId);
            tgChatDao.save(tgChatId);
        }
        try {
            tgChatLinksDao.save(tgChatId, linkId);
        } catch (DuplicateKeyException e) {
            log.warn("Link was added already");
        }

        return LinkResponse.builder()
            .id(linkId)
            .link(url)
            .build();
    }

    @Override
    public List<LinkUpdateResponse> findLinksToCheckForUpdates(Long forceCheckDelay) {
        log.info("Find links to check for updates...");
        return linkDao.findLinksToCheckForUpdates(forceCheckDelay);
    }

    @Transactional
    @Override
    public void updateLink(URI link, OffsetDateTime lastCheck) {
        log.info("Update link " + link + "with new lastCheck: " + lastCheck);
        linkDao.update(link.toString(), lastCheck);
    }

    @Transactional
    @Override
    public void remove(URI link) {
        linkDao.remove(link.toString());
    }
}
