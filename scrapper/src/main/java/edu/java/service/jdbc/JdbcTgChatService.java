package edu.java.service.jdbc;

import edu.java.dao.JdbcTgChatDao;
import edu.java.dto.tgchatlinks.TgChatResponse;
import edu.java.service.TgChatService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JdbcTgChatService implements TgChatService {

    private final JdbcTgChatDao tgChatRepository;

    @Transactional
    @Override
    public void remove(Long id) {
        tgChatRepository.remove(id);
        //        throw new TgChatNotExistException(); //TODO
    }

    @Transactional
    @Override
    public void save(Long id) {
        tgChatRepository.save(id);
        //        throw new TgChatAlreadyRegisteredException(); //TODO
    }

    @Override
    public List<Long> fetchTgChatsIdByLink(URI link) {
        return tgChatRepository.fetchTgChatsIdByLink(link.toString());
    }

    @Override
    public TgChatResponse fetchById(Long id) {
        return tgChatRepository.fetchById(id);
    }
}
