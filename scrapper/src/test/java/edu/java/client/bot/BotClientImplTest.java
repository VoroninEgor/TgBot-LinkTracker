package edu.java.client.bot;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.AbstractTest;
import edu.java.dto.link.LinkUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.net.URI;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@WireMockTest(httpPort = 8090)
class BotClientImplTest extends AbstractTest {

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("app.use-queue", () -> "false");
    }

    @Test
    void updatesPost() {
        var baseUrl = "http://localhost:8090";
        var botClient = new BotClientImpl(baseUrl);
        LinkUpdateRequest linkUpdate = LinkUpdateRequest.builder()
            .id(1L)
            .url(URI.create("http://bot.com"))
            .description("desc")
            .tgChatIds(List.of(1L, 2L))
            .build();
        String expectedJson = jsonToString("src/test/resources/bot.json");
        stubFor(post("/updates")
            .withRequestBody(equalToJson(expectedJson))
            .willReturn(aResponse()));

        assertDoesNotThrow(() -> botClient.send(linkUpdate));

    }
}
