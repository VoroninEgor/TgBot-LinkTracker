package edu.java.bot.client;

import edu.java.bot.dto.AddLinkRequest;
import edu.java.bot.dto.LinkResponse;
import edu.java.bot.dto.ListLinksResponse;
import edu.java.bot.dto.RemoveLinkRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperLinkClientImpl implements ScrapperLinkClient {
    private final static String BASEURL = "http://localhost:8080/";
    private final WebClient webClient;
    private final static String BASE_ENDPOINT = "/links";
    private final static String TGCHAT_ID_HEADER = "Tg-Chat-Id";

    public ScrapperLinkClientImpl() {
        webClient = WebClient.create(BASEURL);
    }

    public ScrapperLinkClientImpl(String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    @Override
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri(BASE_ENDPOINT)
            .header(TGCHAT_ID_HEADER, tgChatId.toString())
            .bodyValue(removeLinkRequest)
            .retrieve()
            .toEntity(LinkResponse.class)
            .block();
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId) {
        return webClient.get()
            .uri(BASE_ENDPOINT)
            .header(TGCHAT_ID_HEADER, tgChatId.toString())
            .retrieve()
            .toEntity(ListLinksResponse.class)
            .block();
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(BASE_ENDPOINT)
            .header(TGCHAT_ID_HEADER, tgChatId.toString())
            .bodyValue(addLinkRequest)
            .retrieve()
            .toEntity(LinkResponse.class)
            .block();
    }
}
