package edu.java.bot.controller;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.service.UpdatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/updates")
public class UpdatesController implements UpdatesApi {

    private final UpdatesService updatesService;

    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest linkUpdate) {
        log.info("/updates POST endpoint");
        updatesService.handleUpdate(linkUpdate);
        return ResponseEntity.ok().build();
    }
}
