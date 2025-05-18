package org.example.inconceptproject.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.Objects;

@Table(name = "publisher")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_id_seq")
    @SequenceGenerator(
            name = "publisher_id_seq",
            sequenceName = "publisher_id_seq",
            allocationSize = 50)
    private Long id;

    private String name;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publisher publisher = (Publisher) o;
        return Objects.equals(name, publisher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Publisher{name='" + name + "'}";
    }
}
