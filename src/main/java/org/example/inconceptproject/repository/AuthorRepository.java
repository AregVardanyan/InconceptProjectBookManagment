package org.example.inconceptproject.repository;

import org.example.inconceptproject.criteria.AuthorSearchCriteria;
import org.example.inconceptproject.dto.response.AuthorStatisticsDTO;
import org.example.inconceptproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    @Query("""
        SELECT new org.example.inconceptproject.dto.response.AuthorStatisticsDTO(a.name, AVG(b.rating), SUM(b.numRatings) , COUNT(b))
        FROM Book b
        JOIN b.authors ba
        JOIN ba.author a
        WHERE (:#{#criteria.name} IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :#{#criteria.name}, '%')))
        GROUP BY a.name
        HAVING 
            (:#{#criteria.minAvgRating} IS NULL OR AVG(b.rating)>= :#{#criteria.minAvgRating})
            AND (:#{#criteria.maxAvgRating} IS NULL OR AVG(b.rating) <= :#{#criteria.maxAvgRating})
            AND (:#{#criteria.minBookCount} IS NULL OR COUNT(b) >= :#{#criteria.minBookCount})
            AND (:#{#criteria.maxBookCount} IS NULL OR COUNT(b) <= :#{#criteria.maxBookCount}) 
    """)
    List<AuthorStatisticsDTO> getAll(@Param("criteria") AuthorSearchCriteria criteria);

//    boolean existsByName(String name);
//
//    Author getAuthorByName(String name);
//
//    @Query("SELECT a.name, AVG(b.rating) AS averageRating " +
//            "FROM Book b JOIN BookAuthor ba ON b = ba.book " +
//            "JOIN Author a ON a = ba.author " +
//            "GROUP BY a.name")
//    List<Object[]> findAvgRatingByAuthor();
//
//
//    @Query("SELECT a.name, AVG(b.rating), COUNT(DISTINCT r.id), COUNT(DISTINCT g.id) " +
//            "FROM Book b JOIN BookAuthor ba ON b = ba.book " +
//            "JOIN Author a ON a = ba.author " +
//            "JOIN Role r ON r = ba.role " +
//            "JOIN b.genres as g " +
//            "GROUP BY a.name, a.id " +
//            "HAVING a.id = ?1")
//    List<Object[]> findAuthorInfo(Long id);
}
