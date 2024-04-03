package edu.java.repository;

import edu.java.entity.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LinkRepo extends JpaRepository<Link, Long> {

    Optional<Link> getLinkByUrl(String url);

    List<Link> findAllByLastCheckBefore(OffsetDateTime beforeThisTime);

    @Transactional
    void deleteByUrl(String url);
}
