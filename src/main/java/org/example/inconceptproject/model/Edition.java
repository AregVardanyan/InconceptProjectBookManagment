package org.example.inconceptproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.Objects;


@Table(name = "edition")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edition_id_seq")
    @SequenceGenerator(
            name = "edition_id_seq",
            sequenceName = "edition_id_seq",
            allocationSize = 50)
    private Long id;

    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edition edition = (Edition) o;
        return Objects.equals(name, edition.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Edition{name='" + name + "'}";
    }
}
