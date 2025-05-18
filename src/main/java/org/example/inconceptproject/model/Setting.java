package org.example.inconceptproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Table(name = "setting")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setting_id_seq")
    @SequenceGenerator(
            name = "setting_id_seq",
            sequenceName = "setting_id_seq",
            allocationSize = 50)
    private Long id;

    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Setting setting = (Setting) o;
        return Objects.equals(name, setting.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Setting{name='" + name + "'}";
    }
}
