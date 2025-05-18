package org.example.inconceptproject.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.Objects;


@Table(name = "language")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_id_seq")
    @SequenceGenerator(
            name = "language_id_seq",
            sequenceName = "language_id_seq",
            allocationSize = 50)
    private Long id;

    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;
        return Objects.equals(name, language.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Language{name='" + name + "'}";
    }
}
