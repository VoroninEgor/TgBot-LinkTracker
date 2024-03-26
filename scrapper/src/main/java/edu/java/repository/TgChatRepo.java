package edu.java.repository;

import edu.java.entity.TgChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TgChatRepo extends JpaRepository<TgChat, Long> {

}
