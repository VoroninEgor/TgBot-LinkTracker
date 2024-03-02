package edu.java.configuration;

import edu.java.client.bot.BotClient;
import edu.java.client.bot.BotClientImpl;
import edu.java.client.github.GitHubClient;
import edu.java.client.github.GitHubClientImpl;
import edu.java.client.stackoverflow.StackOverFlowClient;
import edu.java.client.stackoverflow.StackOverFlowClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClientImpl();
    }

    @Bean
    public StackOverFlowClient stackOverFlowClient() {
        return new StackOverFlowClientImpl();
    }

    @Bean
    public BotClient botClient() {
        return new BotClientImpl();
    }
}
