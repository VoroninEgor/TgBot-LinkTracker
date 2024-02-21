package edu.java.client.github;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.RepoResponse;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class GitHubClientImplTest {

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
        String bodyJson = getBodyJsonWithUpdateAt();

        stubFor(get(format("/%s/%s", owner, repo)).willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(bodyJson)));
    }

    @Test
    void fetchRepo_shouldReturnRepoResponseWithUpdatedAt() {
        RepoResponse repoResponse = gitHubClient.fetchRepo(owner, repo);

        assertEquals(OffsetDateTime.parse("2024-02-02T15:17:25Z"), repoResponse.updatedAt());
    }

    String getBodyJsonWithUpdateAt() {
        return """
            {
                "id": 751897954,
                "owner": {
                    "id": 115171441
                },
                "updated_at": "2024-02-02T15:17:25Z"
            }
                    """;
    }
}
