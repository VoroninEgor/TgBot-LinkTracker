package edu.java.service.jpa;

import edu.java.dto.tgchatlinks.TgChatResponse;
import edu.java.entity.Link;
import edu.java.entity.TgChat;
import edu.java.exception.TgChatAlreadyRegisteredException;
import edu.java.exception.TgChatNotExistException;
import edu.java.repository.LinkRepo;
import edu.java.repository.TgChatRepo;
import edu.java.service.TgChatService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaTgChatService implements TgChatService {

    private final LinkRepo linkRepo;
    private final TgChatRepo tgChatRepo;

    @Transactional
    @Override
    public void unregister(Long id) {
        tgChatRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void register(Long id) {
        TgChat tgChat = new TgChat();
        tgChat.setId(id);
        tgChat.setCreatedAt(OffsetDateTime.now());
        Optional<TgChat> optionalTgChat = tgChatRepo.findById(id);

        if (optionalTgChat.isPresent()) {
            throw new TgChatAlreadyRegisteredException();
        }

        tgChatRepo.save(tgChat);
    }

    @Override
    public List<Long> fetchTgChatsIdByLink(URI link) {
        Link linkByUrl = linkRepo.getLinkByUrl(link.toString()).orElseThrow();

        return linkByUrl.getTgChatLinks().stream()
            .map(v -> v.getChat().getId())
            .toList();
    }

    @Override
    public TgChatResponse fetchById(Long id) {
        try {
            TgChat tgChat = tgChatRepo.findById(id).orElseThrow();
            return TgChatResponse.builder()
                .id(tgChat.getId())
                .createdAt(tgChat.getCreatedAt())
                .build();
        } catch (NoSuchElementException e) {
            throw new TgChatNotExistException();
        }
    }
}
