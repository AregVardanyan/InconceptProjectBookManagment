package org.example.inconceptproject.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorStatisticsDTO {
    private String name;

    private Double avgRating;

    private Long ratingsCount;

    private Long bookCount;
}
