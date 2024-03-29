package edu.java.configuration;

import edu.java.repository.LinkRepo;
import edu.java.repository.TgChatLinkRepo;
import edu.java.repository.TgChatRepo;
import edu.java.service.LinkService;
import edu.java.service.TgChatService;
import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.JpaTgChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    @Bean
    LinkService linkService(LinkRepo linkRepo, TgChatRepo tgChatRepo, TgChatLinkRepo tgChatLinkRepo) {
        return new JpaLinkService(linkRepo, tgChatRepo, tgChatLinkRepo);
    }

    @Bean
    TgChatService tgChatService(TgChatRepo tgChatRepo, LinkRepo linkRepo) {
        return new JpaTgChatService(linkRepo, tgChatRepo);
    }
}
