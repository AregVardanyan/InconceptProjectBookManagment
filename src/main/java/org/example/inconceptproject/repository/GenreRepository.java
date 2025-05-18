package org.example.inconceptproject.repository;

import jakarta.transaction.Transactional;
import org.example.inconceptproject.model.Genre;
import org.example.inconceptproject.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);

    @Query("""
        SELECT g FROM Book b JOIN b.genres bg JOIN bg.genre g
        GROUP BY g.id
        ORDER BY COUNT(b.id) DESC
        LIMIT :top
    """)
    List<Genre> findTopByBookCount(Integer top);


//    boolean existsByName(String genreName);
//
//    @Query("SELECT g FROM Genre g WHERE g.name=?1")
//    Genre getGenreByName(String name);
//
//    @Query("SELECT g.name, COUNT(b.id) " +
//            "FROM Book b " +
//            "JOIN b.genres g " +
//            "GROUP BY g.id")
//    List<Object[]> findAllGenresWithBookCount();
//
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM book_genre WHERE genre_id = :genreId", nativeQuery = true)
//    void deleteBookGenreMappingsByGenreId(@Param("genreId") Long genreId);
}
