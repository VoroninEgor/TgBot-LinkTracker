package edu.java.client.stackoverflow;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.QuestionItem;
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
class StackOverFlowClientImplTest {

    String baseUrl;
    StackOverFlowClientImpl stackOverFlowClient;
    Long id;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:8080";
        stackOverFlowClient = new StackOverFlowClientImpl(baseUrl);
        id = 5L;
        String bodyJson = getBodyJsonWithRequiredData();

        stubFor(get("/" + id + "?site=stackoverflow.com").willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(bodyJson)));
    }

    @Test
    void fetchQuestion_shouldReturnQuestionId() {
        QuestionItem questionItem = stackOverFlowClient.fetchQuestion(id);
        QuestionItem.QuestionResponse first = questionItem.items().getFirst();

        assertEquals(6349421L, first.id());
        assertEquals(
            OffsetDateTime.ofInstant(Instant.ofEpochSecond(1698748588L), ZoneId.of("UTC")),
            first.updatedAt()
        );
    }

    @Test
    void fetchQuestion_shouldReturnQuestionUpdatedAt() {
        QuestionItem questionItem = stackOverFlowClient.fetchQuestion(id);
        QuestionItem.QuestionResponse first = questionItem.items().getFirst();

        assertEquals(
            OffsetDateTime.ofInstant(Instant.ofEpochSecond(1698748588L), ZoneId.of("UTC")),
            first.updatedAt()
        );
    }

    String getBodyJsonWithRequiredData() {
        return """
            {
                "items": [
                    {
                        "last_activity_date": 1698748588,
                        "question_id": 6349421
                    }
                ]
            }
            """;
    }
}
