package org.example.inconceptproject.model.link;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inconceptproject.model.Book;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Genre;

import java.util.Objects;


@Table(name = "book_genre")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_genre_id_seq")
    @SequenceGenerator(
            name = "book_genre_id_seq",
            sequenceName = "book_genre_id_seq",
            allocationSize = 50)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookGenre bookGenre = (BookGenre) o;
        return Objects.equals(book, bookGenre.book)
                && Objects.equals(genre, bookGenre.genre);}

    @Override
    public int hashCode() {
        return Objects.hash(
                book, genre
        );
    }
}
