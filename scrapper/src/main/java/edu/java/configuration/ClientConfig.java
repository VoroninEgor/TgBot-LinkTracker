package edu.java.configuration;

import edu.java.client.github.GitHubClientImpl;
import edu.java.client.stackoverflow.StackOverFlowClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public GitHubClientImpl gitHubClient() {
        return new GitHubClientImpl();
    }

    @Bean
    public StackOverFlowClientImpl stackOverFlowClient() {
        return new StackOverFlowClientImpl();
    }
}
