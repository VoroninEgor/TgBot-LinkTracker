package edu.java.configuration;

import edu.java.dao.jooq.JooqLinkDao;
import edu.java.dao.jooq.JooqTgChatDao;
import edu.java.dao.jooq.JooqTgChatLinksDao;
import edu.java.service.DefaultLinkService;
import edu.java.service.DefaultTgChatService;
import edu.java.service.LinkService;
import edu.java.service.TgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfig {
    @Bean
    LinkService linkService(JooqLinkDao linkDao, JooqTgChatDao tgChatDao, JooqTgChatLinksDao tgChatLinksDao) {
        return new DefaultLinkService(linkDao, tgChatDao, tgChatLinksDao);
    }

    @Bean
    TgChatService tgChatService(JooqTgChatDao tgChatDao) {
        return new DefaultTgChatService(tgChatDao);
    }
}
