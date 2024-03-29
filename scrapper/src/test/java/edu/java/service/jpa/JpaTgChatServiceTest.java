package edu.java.service.jpa;

import edu.java.dto.link.AddLinkRequest;
import edu.java.dto.tgchatlinks.TgChatResponse;
import edu.java.exception.TgChatAlreadyRegisteredException;
import edu.java.exception.TgChatNotExistException;
import edu.java.repository.LinkRepo;
import edu.java.repository.TgChatLinkRepo;
import edu.java.repository.TgChatRepo;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JpaTgChatServiceTest extends IntegrationTest {

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
    void register() {
        Long countBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tgchats", Long.class);

        tgChatService.register(322L);

        Long countAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tgchats", Long.class);

        assertEquals(1, countAfter - countBefore);
    }

    @Test
    void shouldThrowTgChatAlreadyRegisteredException() {
        tgChatService.register(322L);

        assertThrows(TgChatAlreadyRegisteredException.class, () -> tgChatService.register(322L));
    }

    @Test
    void unregister() {
        tgChatService.register(322L);

        Long countBefore = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tgchats", Long.class);
        tgChatService.unregister(322L);
        Long countAfter = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tgchats", Long.class);

        assertEquals(1L, countBefore - countAfter);
    }

    @Test
    void fetchTgChatsIdByLink() {
        URI url = URI.create("URL");
        AddLinkRequest request = new AddLinkRequest(url);
        tgChatService.register(1L);
        tgChatService.register(2L);
        linkService.trackLinkForUser(1L, request);
        linkService.trackLinkForUser(2L, request);

        List<Long> tgChatIds = tgChatService.fetchTgChatsIdByLink(url);

        assertEquals(2, tgChatIds.size());
        assertThat(tgChatIds).containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    void fetchById() {
        tgChatService.register(322L);
        TgChatResponse tgChatResponse = tgChatService.fetchById(322L);

        assertEquals(322L, tgChatResponse.id());
    }

    @Test
    void fetchByIdShouldThrowTgChatNotExistException() {
        assertThrows(TgChatNotExistException.class, () -> tgChatService.fetchById(322L));
    }
}
