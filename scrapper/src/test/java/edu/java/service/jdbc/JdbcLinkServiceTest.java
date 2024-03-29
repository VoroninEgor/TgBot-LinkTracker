package edu.java.service.jdbc;

import edu.java.dto.link.LinkUpdateResponse;
import edu.java.scrapper.IntegrationTest;
import edu.java.service.LinkService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JdbcLinkServiceTest extends IntegrationTest {

    @Autowired
    LinkService linkService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void DBAccessProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jdbc");
    }

    @Transactional
    @Test
    @Sql("/schema/insert-links-table.sql")
    void findLinksToCheckForUpdates() {
        List<LinkUpdateResponse> linksToCheckForUpdates = linkService.findLinksToCheckForUpdates(10L);
        assertEquals(1, linksToCheckForUpdates.size());
        assertEquals("url2", linksToCheckForUpdates.getFirst().url().toString());
    }
}
