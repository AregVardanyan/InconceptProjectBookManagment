package org.example.inconceptproject.model.link;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inconceptproject.model.Author;
import org.example.inconceptproject.model.Book;
import org.example.inconceptproject.model.AuthorRole;

import java.util.Objects;


@Table(name = "book_author")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_author_id_seq")
    @SequenceGenerator(
            name = "book_author_id_seq",
            sequenceName = "book_author_id_seq",
            allocationSize = 50)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_role_id")
    private AuthorRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookAuthor bookAuthor = (BookAuthor) o;
        return Objects.equals(book, bookAuthor.book)
                && Objects.equals(author, bookAuthor.author)
                && Objects.equals(role, bookAuthor.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                book, author, role
        );
    }
}

