package edu.java.bot.controller;

import edu.java.bot.service.UpdatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.notes.api.UpdatesApi;
import ru.tinkoff.notes.model.LinkUpdate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UpdatesController implements UpdatesApi {

    private final UpdatesService updatesService;

    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdate linkUpdate) {
        log.info("/updates POST endpoint");
        updatesService.handleUpdate(linkUpdate);
        return ResponseEntity.ok().build();
    }
}
