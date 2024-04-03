package edu.java.service;

import edu.java.dao.TgChatDao;
import edu.java.dto.tgchatlinks.TgChatResponse;
import edu.java.exception.TgChatAlreadyRegisteredException;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultTgChatService implements TgChatService {

    private final TgChatDao tgChatDao;

    @Transactional
    @Override
    public void unregister(Long id) {
        tgChatDao.remove(id);
    }

    @Transactional
    @Override
    public void register(Long id) {
        try {
            tgChatDao.save(id);
        } catch (DuplicateKeyException e) {
            throw new TgChatAlreadyRegisteredException();
        }
    }

    @Override
    public List<Long> fetchTgChatsIdByLink(URI link) {
        return tgChatDao.fetchTgChatsIdByLink(link.toString());
    }

    @Override
    public TgChatResponse fetchById(Long id) {
        return tgChatDao.fetchById(id);
    }
}
