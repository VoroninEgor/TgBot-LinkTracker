package edu.java.configuration;

import edu.java.dao.jooq.JooqLinkDao;
import edu.java.dao.jooq.JooqTgChatDao;
import edu.java.dao.jooq.JooqTgChatLinksDao;
import edu.java.service.LinkService;
import edu.java.service.TgChatService;
import edu.java.service.jooq.JooqLinkService;
import edu.java.service.jooq.JooqTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfig {
    @Bean
    LinkService linkService(JooqLinkDao linkDao, JooqTgChatDao tgChatDao, JooqTgChatLinksDao tgChatLinksDao) {
        return new JooqLinkService(linkDao, tgChatDao, tgChatLinksDao);
    }

    @Bean
    TgChatService tgChatService(JooqTgChatDao tgChatDao) {
        return new JooqTgChatService(tgChatDao);
    }
}
