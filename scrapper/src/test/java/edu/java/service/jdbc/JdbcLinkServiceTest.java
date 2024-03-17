package edu.java.service.jdbc;

import edu.java.dto.link.LinkUpdateResponse;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JdbcLinkServiceTest extends IntegrationTest {

    @Autowired
    JdbcLinkService linkService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO links (id, url, last_check) values (1, 'url1', now())");
        jdbcTemplate.update("INSERT INTO links (id, url, last_check) values (2, 'url2', '1970-01-02')");
    }

    @Transactional
    @Test
    void findLinksToCheckForUpdates() {
        List<LinkUpdateResponse> linksToCheckForUpdates = linkService.findLinksToCheckForUpdates(10L);
        assertEquals(1, linksToCheckForUpdates.size());
        assertEquals("url2", linksToCheckForUpdates.getFirst().url().toString());
    }
}
