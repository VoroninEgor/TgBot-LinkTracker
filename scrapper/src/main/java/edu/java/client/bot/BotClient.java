package edu.java.client.bot;

import edu.java.dto.LinkUpdateRequest;

public interface BotClient {
    void updatesPost(LinkUpdateRequest linkUpdate);
}
