package org.example.inconceptproject.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CharacterDto {
    private Long id;
    private String name;
    private Long seriesId;
    private String seriesName;
}
