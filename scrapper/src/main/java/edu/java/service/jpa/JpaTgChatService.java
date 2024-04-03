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
        Optional<TgChat> optionalTgChat = tgChatRepo.findById(id);

        if (optionalTgChat.isPresent()) {
            throw new TgChatAlreadyRegisteredException();
        }

        TgChat tgChat = new TgChat();
        tgChat.setId(id);
        tgChat.setCreatedAt(OffsetDateTime.now());

        tgChatRepo.save(tgChat);
    }

    @Override
    public List<Long> fetchTgChatsIdByLink(URI link) {
        Link linkByUrl = linkRepo.findLinkByUrl(link.toString()).orElseThrow();

        return linkByUrl.getTgChatLinks().stream()
            .map(v -> v.getChat().getId())
            .toList();
    }

    @Override
    public TgChatResponse fetchById(Long id) {
        TgChat tgChat = tgChatRepo.findById(id).orElseThrow(TgChatNotExistException::new);
        return TgChatResponse.builder()
            .id(tgChat.getId())
            .createdAt(tgChat.getCreatedAt())
            .build();
    }
}
