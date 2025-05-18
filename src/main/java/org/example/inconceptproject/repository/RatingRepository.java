package org.example.inconceptproject.repository;

import org.example.inconceptproject.model.RatingByStars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingByStars, Long> {

//    Optional<RatingByStars> findByBookIdAndStars(Long bookId, Integer stars);
}
