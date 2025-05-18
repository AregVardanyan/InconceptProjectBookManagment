package org.example.inconceptproject.model.link;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inconceptproject.model.Book;
import org.example.inconceptproject.model.Characterr;
import org.hibernate.annotations.BatchSize;

import java.util.Objects;

@Table(name = "book_character")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data

public class BookCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_character_id_seq")
    @SequenceGenerator(
            name = "book_character_id_seq",
            sequenceName = "book_character_id_seq",
            allocationSize = 50)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "character_id")
    private Characterr character;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookCharacter bookCharacter = (BookCharacter) o;
        return Objects.equals(book, bookCharacter.book)
                && Objects.equals(character, bookCharacter.character);}

    @Override
    public int hashCode() {
        return Objects.hash(
                book, character
        );
    }
}
