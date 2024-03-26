package edu.java.repository;

import edu.java.entity.TgChatLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TgChatLinkRepo extends JpaRepository<TgChatLink, Long> {

}
