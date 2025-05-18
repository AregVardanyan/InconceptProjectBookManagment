package org.example.inconceptproject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenreDto {
    private Long id;
    private String name;
}
