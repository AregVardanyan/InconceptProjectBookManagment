package org.example.inconceptproject.repository;

import jakarta.transaction.Transactional;
import org.example.inconceptproject.model.Language;
import org.example.inconceptproject.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);

//    boolean existsByName(String publisherName);
//
//    @Query("SELECT p.name FROM Publisher p")
//    Set<String> findAllNames();
//
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE book SET publisher_id = NULL WHERE publisher_id = :publisherId", nativeQuery = true)
//    void nullifyPublisherInBooks(Long publisherId);
}
