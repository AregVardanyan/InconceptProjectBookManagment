package org.example.inconceptproject.repository;

import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Series;
import org.example.inconceptproject.service.BookService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByName(String name);

    @Query("""
    SELECT DISTINCT c.name FROM Characterr c JOIN c.series cs
        WHERE cs.id = :seriesId
    """)
    List<String> findAllCharacters(Long seriesId);
}
