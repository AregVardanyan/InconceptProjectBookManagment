package org.example.inconceptproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@Table(name = "genre")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_id_seq")
    @SequenceGenerator(
            name = "genre_id_seq",
            sequenceName = "genre_id_seq",
            allocationSize = 50)
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Genre{name='" + name + "'}";
    }


}



