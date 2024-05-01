package edu.java.service.jpa;

import edu.java.AbstractIntegrationTest;
import edu.java.dto.link.AddLinkRequest;
import edu.java.dto.tgchatlinks.TgChatResponse;
import edu.java.exception.TgChatAlreadyRegisteredException;
import edu.java.exception.TgChatNotExistException;
import edu.java.service.LinkService;
import edu.java.service.TgChatService;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JpaTgChatServiceTest extends AbstractIntegrationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TgChatService tgChatService;
    @Autowired
    LinkService linkService;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM tgchats WHERE 1=1");
        jdbcTemplate.update("DELETE FROM links WHERE 1=1");
    }

    @DynamicPropertySource
    static void DBAccessProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
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
