package edu.java.configuration;

import edu.java.client.bot.BotClientImpl;
import edu.java.client.github.GitHubClient;
import edu.java.client.github.GitHubClientImpl;
import edu.java.client.stackoverflow.StackOverFlowClient;
import edu.java.client.stackoverflow.StackOverFlowClientImpl;
import edu.java.kafka.service.DataSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    @ConditionalOnProperty(prefix = "app", value = "use-queue", havingValue = "false")
    public DataSender botClient() {
        return new BotClientImpl();
    }
}
