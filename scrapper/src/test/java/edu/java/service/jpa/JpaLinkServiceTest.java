package edu.java.service.jpa;

import edu.java.dto.link.AddLinkRequest;
import edu.java.dto.link.LinkUpdateResponse;
import edu.java.dto.link.ListLinksResponse;
import edu.java.dto.link.RemoveLinkRequest;
import edu.java.entity.Link;
import edu.java.entity.TgChat;
import edu.java.repository.LinkRepo;
import edu.java.repository.TgChatLinkRepo;
import edu.java.repository.TgChatRepo;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JpaLinkServiceTest extends IntegrationTest {

    @Autowired
    LinkRepo linkRepo;
    @Autowired
    TgChatRepo tgChatRepo;
    @Autowired
    TgChatLinkRepo tgChatLinkRepo;
    @Autowired
    JdbcTemplate jdbcTemplate;

    JpaTgChatService tgChatService;
    JpaLinkService linkService;

    @BeforeEach
    void setUp() {
        tgChatService = new JpaTgChatService(linkRepo, tgChatRepo);
        linkService = new JpaLinkService(linkRepo, tgChatRepo, tgChatLinkRepo);
        jdbcTemplate.update("DELETE FROM tgchats WHERE 1=1");
        jdbcTemplate.update("DELETE FROM links WHERE 1=1");
    }

    @Test
    void untrackLinkForUser() {
        tgChatService.register(5L);
        String url = "URL";
        linkService.trackLinkForUser(5L, new AddLinkRequest(URI.create(url)));

        Long sizeBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tgchat_links", Long.class);

        linkService.untrackLinkForUser(5L, new RemoveLinkRequest(URI.create(url)));

        Long sizeAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tgchat_links", Long.class);

        assertEquals(1, sizeBefore - sizeAfter);
    }

    @Test
    void getLinksByTgChatId() {
        tgChatService.register(5L);
        String url1 = "URL1";
        String url2 = "URL2";
        linkService.trackLinkForUser(5L, new AddLinkRequest(URI.create(url1)));
        linkService.trackLinkForUser(5L, new AddLinkRequest(URI.create(url2)));

        ListLinksResponse linksByTgChatId = linkService.getLinksByTgChatId(5L);

        assertEquals(2, linksByTgChatId.size());
    }

    @Test
    void trackLinkForUser() {
        tgChatService.register(5L);
        String url = "URL";
        linkService.trackLinkForUser(5L, new AddLinkRequest(URI.create(url)));

        TgChat tgChat = tgChatRepo.findById(5L).orElseThrow();
        Long tgChatLinksCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tgchat_links", Long.class);

        assertEquals(1, tgChatLinksCount);
        assertEquals(1, tgChat.getTgChatLinks().size());
        assertEquals(url, tgChat.getTgChatLinks().getFirst().getLink().getUrl());
    }

    @Test
    void findLinksToCheckForUpdatesShouldSeekNothing() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        linkRepo.save(Link.builder().url("url1").lastCheck(now.minusMinutes(12)).build());
        linkRepo.save(Link.builder().url("url2").lastCheck(now.minusMinutes(14)).build());
        linkRepo.save(Link.builder().url("url3").lastCheck(now.minusMinutes(16)).build());

        List<LinkUpdateResponse> linksToCheckForUpdates = linkService.findLinksToCheckForUpdates(17L);

        assertEquals(0, linksToCheckForUpdates.size());
    }

    @Test
    void findLinksToCheckForUpdatesShouldSeek2() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        linkRepo.save(Link.builder().url("url1").lastCheck(now.minusMinutes(12)).build());
        linkRepo.save(Link.builder().url("url2").lastCheck(now.minusMinutes(14)).build());
        linkRepo.save(Link.builder().url("url3").lastCheck(now.minusMinutes(16)).build());

        List<LinkUpdateResponse> linksToCheckForUpdates = linkService.findLinksToCheckForUpdates(13L);

        assertEquals(2, linksToCheckForUpdates.size());
    }

    @Test
    void updateLink() {
        String url = "URL";
        linkRepo.save(Link.builder().url(url).build());

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        linkService.updateLink(URI.create(url), now);

        Link linkByUrlAfterUpdate = linkRepo.getLinkByUrl(url).orElseThrow();

        assertEquals(now.withNano(0), linkByUrlAfterUpdate.getLastCheck().withNano(0));
    }

    @Test
    void remove() {
        tgChatService.register(5L);
        URI url = URI.create("URL");
        linkService.trackLinkForUser(5L, new AddLinkRequest(url));

        Long before = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM links", Long.class);

        linkService.remove(url);

        Long after = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM links", Long.class);

        assertEquals(1, before - after);
    }
}
