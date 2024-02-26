package edu.java.controller;

import edu.java.service.TgChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.notes.api.TgChatApi;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TgChatController implements TgChatApi {

    private final TgChatService tgChatService;

    @Override
    public ResponseEntity<Void> tgChatIdDelete(Long id) {
        log.info("/tg-chat/{id} DELETE endpoint");
        tgChatService.removeById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> tgChatIdPost(Long id) {
        log.info("/tg-chat/{id} POST endpoint");
        tgChatService.save(id);
        return ResponseEntity.ok().build();
    }
}
