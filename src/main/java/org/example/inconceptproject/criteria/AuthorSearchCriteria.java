package org.example.inconceptproject.criteria;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSearchCriteria extends SearchCriteria{
    private String name;

    private Double maxAvgRating;
    private Double minAvgRating;

    private Long maxBookCount;
    private Long minBookCount;
}
