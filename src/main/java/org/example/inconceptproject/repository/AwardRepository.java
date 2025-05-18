package org.example.inconceptproject.repository;

import jakarta.transaction.Transactional;
import org.example.inconceptproject.model.Award;
import org.example.inconceptproject.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
    Optional<Award> findByName(String name);

//    boolean existsByName(String bookFormat);
//
//
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM book_award WHERE award_id = :awardId", nativeQuery = true)
//    void deleteBookAwardMappingsByAwardId(@Param("awardId") Long awardId);
}
