package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.notes.model.LinkUpdate;

@Service
@RequiredArgsConstructor
public class UpdatesService {

    private final Bot bot;

    public void handleUpdate(LinkUpdate linkUpdate) {
        linkUpdate.getTgChatIds().stream()
            .map(id -> new SendMessage(id, linkUpdate.getDescription()))
            .forEach(bot::execute);
    }
}
