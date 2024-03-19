package edu.java.client.github;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.AbstractTest;
import edu.java.dto.github.RepoResponse;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class GitHubClientImplTest extends AbstractTest {

    String baseUrl;
    GitHubClientImpl gitHubClient;
    String owner;
    String repo;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:8080";
        gitHubClient = new GitHubClientImpl(baseUrl);
        owner = "owner";
        repo = "repo";
        String bodyJson = jsonToString("src/test/resources/github.json");

        stubFor(get(format("/repos/%s/%s", owner, repo)).willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(bodyJson)));
    }

    @Test
    void fetchRepo_shouldReturnRepoResponseWithUpdatedAt() {
        RepoResponse repoResponse = gitHubClient.fetchRepo(owner, repo);

        assertEquals(OffsetDateTime.parse("2024-02-02T15:17:25Z"), repoResponse.updatedAt());
    }
}
