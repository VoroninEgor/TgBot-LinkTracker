package edu.java.client.stackoverflow;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.AbstractTest;
import edu.java.dto.QuestionResponse;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class StackOverFlowClientImplTest extends AbstractTest {

    String baseUrl;
    StackOverFlowClientImpl stackOverFlowClient;
    Long id;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:8080";
        stackOverFlowClient = new StackOverFlowClientImpl(baseUrl);
        id = 5L;
        String bodyJson = jsonToString("src/test/resources/stackoverflow.json");

        stubFor(get("/questions/" + id + "?site=stackoverflow.com").willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(bodyJson)));
    }

    @Test
    void fetchQuestion_shouldReturnQuestionId() {
        QuestionResponse questionResponse = stackOverFlowClient.fetchQuestion(id);
        QuestionResponse.QuestionItem first = questionResponse.items().getFirst();

        assertEquals(6349421L, first.id());
    }

    @Test
    void fetchQuestion_shouldReturnQuestionUpdatedAt() {
        QuestionResponse questionResponse = stackOverFlowClient.fetchQuestion(id);
        QuestionResponse.QuestionItem first = questionResponse.items().getFirst();

        assertEquals(
            OffsetDateTime.ofInstant(Instant.ofEpochSecond(1698748588L), ZoneId.of("UTC")),
            first.updatedAt()
        );
    }
}
