package org.example.inconceptproject.criteria;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.example.inconceptproject.enums.SortDirection;
import org.example.inconceptproject.enums.SortBy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchCriteria extends SearchCriteria {

    private String title;
    private String series;

    private Float minRating;
    private Float minLikedPercent;

    private LocalDate publishDateStart;
    private LocalDate publishDateEnd;

    private Float minPrice;
    private Float maxPrice;

    private Integer minPages;
    private Integer maxPages;

    private SortBy sortBy;
    private SortDirection sortDirection;

    private String language;
    private String format;
    private String edition;
    private String publisher;

    private List<String> genresInclude = new ArrayList<>();
    private List<String> genresExclude = new ArrayList<>();

    private String authorName;



    @Override
    public PageRequest buildPageRequest() {
        PageRequest pageRequest = super.buildPageRequest();

        if (sortBy == null) {
            return pageRequest;
        }

        String sortField;
        switch (sortBy) {
            case BBE_SCORE:
                sortField = "bbeScore";
                break;
            case PRICE:
                sortField = "price";
                break;
            case MOST_LIKED:
                sortField = "likedPercent";
                break;
            case LONGEST:
                sortField = "pages";
                break;
            case RATING:
                sortField = "rating";
                break;
            case  MOST_RATED:
                sortField = "numRatings";
                break;
            default:
                sortField = "id";
        }

        Sort sort = (sortDirection == SortDirection.ASC)
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        return pageRequest.withSort(sort);
    }
}
