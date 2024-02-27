package edu.java.client.github;

import edu.java.dto.RepoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import static java.lang.String.format;

@Slf4j
public class GitHubClientImpl implements GitHubClient {
    private final static String BASEURL = "https://api.github.com/";
    private final WebClient webClient;

    public GitHubClientImpl(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    public GitHubClientImpl() {
        webClient = WebClient.create(BASEURL);
    }

    @Override
    public RepoResponse fetchRepo(String owner, String repo) {
        return webClient
            .get()
            .uri(format("repos/%s/%s", owner, repo))
            .retrieve()
            .bodyToMono(RepoResponse.class)
            .block();
    }
}
