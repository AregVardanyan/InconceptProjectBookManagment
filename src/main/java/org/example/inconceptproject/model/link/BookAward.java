package org.example.inconceptproject.model.link;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inconceptproject.model.Award;
import org.example.inconceptproject.model.Book;
import org.hibernate.annotations.BatchSize;

import java.util.Objects;

@Table(name = "book_award")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data

public class BookAward {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_award_id_seq")
    @SequenceGenerator(
            name = "book_award_id_seq",
            sequenceName = "book_award_id_seq",
            allocationSize = 50)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "award_id")
    private Award award;

    private Long year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookAward bookAward = (BookAward) o;
        return Objects.equals(book, bookAward.book)
                && Objects.equals(award, bookAward.award)
                && Objects.equals(year, bookAward.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                book, award, year
        );
    }
}
