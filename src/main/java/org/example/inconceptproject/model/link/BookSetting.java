package org.example.inconceptproject.model.link;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inconceptproject.model.Book;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Setting;

import java.util.Objects;

@Table(name = "book_setting")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data

public class BookSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_setting_id_seq")
    @SequenceGenerator(
            name = "book_setting_id_seq",
            sequenceName = "book_setting_id_seq",
            allocationSize = 50)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "setting_id")
    private Setting setting;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookSetting bookSetting = (BookSetting) o;
        return Objects.equals(book, bookSetting.book)
                && Objects.equals(setting, bookSetting.setting);}

    @Override
    public int hashCode() {
        return Objects.hash(
                book, setting
        );
    }
}
