package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatesService {

    private final Bot bot;

    public void handleUpdate(LinkUpdateRequest linkUpdate) {
        linkUpdate.tgChatIds().stream()
            .map(id -> new SendMessage(id, linkUpdate.description()))
            .forEach(bot::execute);
    }
}
