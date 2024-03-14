package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperLinkClient;
import edu.java.bot.client.ScrapperLinkClientImpl;
import edu.java.bot.client.ScrapperTgChatClient;
import edu.java.bot.client.ScrapperTgChatClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public ScrapperLinkClient scrapperLinkClient() {
        return new ScrapperLinkClientImpl();
    }

    @Bean
    public ScrapperTgChatClient scrapperTgChatClient() {
        return new ScrapperTgChatClientImpl();
    }
}
