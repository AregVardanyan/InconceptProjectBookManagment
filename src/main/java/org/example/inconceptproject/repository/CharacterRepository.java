package org.example.inconceptproject.repository;

import jakarta.transaction.Transactional;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Characterr, Long> {
    Optional<Characterr> findByName(String name);

//    boolean existsByName(String characterName);
//
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM book_character WHERE character_id = :characterId", nativeQuery = true)
//    void deleteBookCharacterMappingsByCharacterId(@Param("characterId") Long characterId);

}
