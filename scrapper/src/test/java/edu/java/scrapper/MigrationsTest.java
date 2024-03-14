package edu.java.scrapper;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MigrationsTest extends IntegrationTest{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void initTablesShouldBeCrested() throws IOException, InterruptedException {
        String linksTableExistQuery = sqlQueryTableExists("links");
        String tgchatsTableExistQuery = sqlQueryTableExists("tgchats");
        String tgchatLinksTableExistQuery = sqlQueryTableExists("tgchat_links");

        Boolean linksIsExists = jdbcTemplate.queryForObject(linksTableExistQuery, Boolean.class);
        Boolean tgchatsIsExists = jdbcTemplate.queryForObject(tgchatsTableExistQuery, Boolean.class);
        Boolean tgchatLinksIsExists = jdbcTemplate.queryForObject(tgchatLinksTableExistQuery, Boolean.class);

        assertTrue(linksIsExists);
        assertTrue(tgchatsIsExists);
        assertTrue(tgchatLinksIsExists);
    }

    @Test
    void invalidTableNotExist() {
        String invalidTableExistQuery = sqlQueryTableExists("invalid");

        Boolean invalidIsExist = jdbcTemplate.queryForObject(invalidTableExistQuery, Boolean.class);

        assertFalse(invalidIsExist);
    }


    public String sqlQueryTableExists(String tableName) {
        return format("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = '%s')", tableName);
    }
}
