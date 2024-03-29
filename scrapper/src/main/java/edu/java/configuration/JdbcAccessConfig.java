package edu.java.configuration;

import edu.java.dao.jdbc.JdbcLinkDao;
import edu.java.dao.jdbc.JdbcTgChatDao;
import edu.java.dao.jdbc.JdbcTgChatLinksDao;
import edu.java.service.LinkService;
import edu.java.service.TgChatService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {
    @Bean
    LinkService linkService(JdbcLinkDao linkDao, JdbcTgChatDao tgChatDao, JdbcTgChatLinksDao tgChatLinksDao) {
        return new JdbcLinkService(linkDao, tgChatDao, tgChatLinksDao);
    }

    @Bean
    TgChatService tgChatService(JdbcTgChatDao tgChatDao) {
        return new JdbcTgChatService(tgChatDao);
    }
}
