package org.example.inconceptproject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;

import java.util.Objects;

@Table(
        name = "award"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "award_id_seq")
    @SequenceGenerator(
            name = "award_id_seq",
            sequenceName = "award_id_seq",
            allocationSize = 50)
    private Long id;

    private String name;

    @Override
    public String toString() {
        return name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Award award = (Award) o;
        return Objects.equals(name, award.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
