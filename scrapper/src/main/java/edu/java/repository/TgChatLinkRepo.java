package edu.java.repository;

import edu.java.entity.TgChatLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TgChatLinkRepo extends JpaRepository<TgChatLink, Long> {
    @Query("DELETE FROM TgChatLink t where t.id = :id")
    @Modifying
    void deleteById(Long id);
}
