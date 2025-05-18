package org.example.inconceptproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.Objects;

@Table(name = "rating")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@BatchSize(size = 4000)
public class RatingByStars {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_by_stars_id_seq")
    @SequenceGenerator(
            name = "rating_by_stars_id_seq",
            sequenceName = "rating_by_stars_id_seq",
            allocationSize = 50)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(columnDefinition = "INT CHECK (stars IN (1, 2, 3, 4, 5))", name = "stars")
    private Integer stars;

    @Column
    private Integer count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingByStars s = (RatingByStars) o;
        return Objects.equals(book, s.book) && Objects.equals(stars, s.stars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, stars);
    }

    @Override
    public String toString() {
        return book.getNaturalBookId() + "s" +stars;
    }
}


