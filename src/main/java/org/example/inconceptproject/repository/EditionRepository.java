package org.example.inconceptproject.repository;

import jakarta.transaction.Transactional;
import org.example.inconceptproject.model.Edition;
import org.example.inconceptproject.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {
    Optional<Edition> findByName(String name);

    boolean existsByName(String editionName);

    @Query("SELECT e.name FROM Edition e")
    Set<String> findAllNames();

    @Modifying
    @Transactional
    @Query(value = "UPDATE book SET edition_id = NULL WHERE edition_id = :editionId", nativeQuery = true)
    void nullifyEditionInBooks(Long editionId);


}
