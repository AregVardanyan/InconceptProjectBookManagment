package org.example.inconceptproject.repository;

import jakarta.transaction.Transactional;
import org.example.inconceptproject.model.Format;
import org.example.inconceptproject.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long>  {
    Optional<Format> findByName(String name);

    boolean existsByName(String name);

    Format getByName(String formatName);

    @Query("SELECT f.name FROM Format f")
    Set<String> findAllNames();

    @Modifying
    @Transactional
    @Query(value = "UPDATE book SET book_format_id = NULL WHERE book_format_id = :formatId", nativeQuery = true)
    void nullifyFormatInBooks(Long formatId);
}
