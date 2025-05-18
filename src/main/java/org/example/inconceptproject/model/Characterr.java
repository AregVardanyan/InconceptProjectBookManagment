package org.example.inconceptproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Table(name = "character")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Characterr {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_id_seq")
    @SequenceGenerator(
            name = "character_id_seq",
            sequenceName = "character_id_seq",
            allocationSize = 50)
    private Long id;

    private String name;

    @ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "series_id")
    private Series series;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Characterr characterr = (Characterr) o;
        return Objects.equals(name, characterr.name) &&
                Objects.equals(series, characterr.series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, series);
    }

    @Override
    public String toString() {
        return "Edition{name='" + name + "'}";
    }
}


