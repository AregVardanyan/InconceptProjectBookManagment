package org.example.inconceptproject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.inconceptproject.model.Award;

@Getter
@Setter
@Builder
public class AwardDto {
    private Long id;
    private String name;
    private Long year;
}

