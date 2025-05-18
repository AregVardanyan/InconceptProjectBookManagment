package org.example.inconceptproject.repository;

import org.example.inconceptproject.criteria.BookSearchCriteria;
import org.example.inconceptproject.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"authors", "genres"})
    @Query("""
    SELECT DISTINCT b
    FROM Book b
    LEFT JOIN b.series s
    LEFT JOIN b.genres bg
    LEFT JOIN bg.genre g
    LEFT JOIN b.authors ba
    LEFT JOIN ba.author a
    WHERE (:#{#criteria.title} IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :#{#criteria.title}, '%')))
      AND (:#{#criteria.series} IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :#{#criteria.series}, '%')))
      AND (:#{#criteria.minRating} IS NULL OR b.rating >= :#{#criteria.minRating})
      AND (:#{#criteria.minLikedPercent} IS NULL OR b.likedPercent >= :#{#criteria.minLikedPercent})
      AND (:#{#criteria.publishDateStart} IS NULL OR b.publishDate >= :#{#criteria.publishDateStart})
      AND (:#{#criteria.publishDateEnd} IS NULL OR b.publishDate <= :#{#criteria.publishDateEnd})
      AND (:#{#criteria.minPrice} IS NULL OR b.price >= :#{#criteria.minPrice})
      AND (:#{#criteria.maxPrice} IS NULL OR b.price <= :#{#criteria.maxPrice})
      AND (:#{#criteria.minPages} IS NULL OR b.pages >= :#{#criteria.minPages})
      AND (:#{#criteria.maxPages} IS NULL OR b.pages <= :#{#criteria.maxPages})
      AND (:#{#criteria.edition} IS NULL OR b.edition = :#{#criteria.edition})
      AND (:#{#criteria.publisher} IS NULL OR b.publisher = :#{#criteria.publisher})
      AND (:#{#criteria.language} IS NULL OR b.language = :#{#criteria.language})
      AND (:#{#criteria.format} IS NULL OR b.bookFormat = :#{#criteria.format})
      AND (:#{#criteria.authorName} IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :#{#criteria.authorName}, '%')))
      GROUP BY b.id, a.id
      HAVING COUNT(CASE WHEN g.name IN :#{#criteria.genresInclude} THEN g.name ELSE NULL END) = :#{#criteria.genresInclude.size()} AND
          COUNT(CASE WHEN g.name IN :#{#criteria.genresExclude} THEN g.name ELSE NULL END) = 0

    """)
    Page<Book> findAll(BookSearchCriteria criteria, Pageable pageable);

    boolean existsBookById(Long id);

    boolean existsBookByTitle(String title);

    boolean existsBookByNaturalBookId(Long naturalBookId);
}
