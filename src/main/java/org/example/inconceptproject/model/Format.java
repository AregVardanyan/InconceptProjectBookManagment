package org.example.inconceptproject.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLInsert;

import java.util.List;
import java.util.Objects;

@Table(name = "format")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "format_id_seq")
    @SequenceGenerator(
            name = "format_id_seq",
            sequenceName = "format_id_seq",
            allocationSize = 50)
    private Long id;

    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Format format = (Format) o;
        return Objects.equals(name, format.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Format{name='" + name + "'}";
    }
}
