package org.example.inconceptproject.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.example.inconceptproject.model.*;
import org.example.inconceptproject.model.link.BookCharacter;
import org.example.inconceptproject.model.link.BookGenre;
import org.example.inconceptproject.model.link.BookSetting;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
public class BookInfoDTO implements Serializable {

    private Long id;
    private Long naturalBookId;
    private String title;
    private String series;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private Float rating;

    private String isbn;
    private Integer pages;
    private Integer likedPercent;
    private LocalDate publishDate;
    private LocalDate firstPublishDate;
    private Integer numRatings;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private Float price;

    private String coverImg;
    private Integer bbeScore;
    private Integer bbeVotes;

    private String language;
    private String format;
    private String edition;
    private String publisher;

    private List<AwardDto> awards;
    private List<SettingDto> settings;
    private List<GenreDto> genres;
    private List<CharacterDto> characters;
    private List<AuthorDto> authors;
    private Map<Integer, Integer> ratingByStars;

    public static BookInfoDTO toDto(Book book) {
        return BookInfoDTO.builder()
                .id(book.getId())
                .naturalBookId(book.getNaturalBookId())
                .title(book.getTitle())
                .series(book.getSeries().getName())
                .description(book.getDescription())
                .rating(book.getRating())
                .isbn(book.getIsbn())
                .pages(book.getPages())
                .likedPercent(book.getLikedPercent())
                .publishDate(book.getPublishDate())
                .firstPublishDate(book.getFirstPublishDate())
                .numRatings(book.getNumRatings())
                .price(book.getPrice())
                .coverImg(book.getCoverImg().getFilePath())
                .bbeScore(book.getBbeScore())
                .bbeVotes(book.getBbeVotes())

                .language(book.getLanguage() != null ? book.getLanguage().getName() : null)
                .format(book.getBookFormat() != null ? book.getBookFormat().getName() : null)
                .edition(book.getEdition() != null ? book.getEdition().getName() : null)
                .publisher(book.getPublisher() != null ? book.getPublisher().getName() : null)

                .awards(book.getAwards() != null
                        ? book.getAwards().stream()
                        .map(a -> AwardDto.builder()
                                .id(a.getAward().getId())
                                .name(a.getAward().getName())
                                .year(a.getYear())
                                .build()).toList()
                        : List.of())

                .settings(book.getSettings() != null
                        ? book.getSettings().stream()
                        .map(a -> SettingDto.builder()
                                .id(a.getSetting().getId())
                                .name(a.getSetting().getName())
                                .build())
                        .toList() : List.of())

                .genres(book.getGenres() != null
                        ? book.getGenres().stream()
                        .map(a -> GenreDto.builder()
                                .id(a.getGenre().getId())
                                .name(a.getGenre().getName())
                                .build())
                        .toList() : List.of())

                .characters(book.getCharacters() != null
                        ? book.getCharacters().stream()
                        .map(a -> CharacterDto.builder()
                                .id(a.getCharacter().getId())
                                .name(a.getCharacter().getName())
                                .seriesId(a.getCharacter().getSeries().getId())
                                .seriesName(a.getCharacter().getSeries().getName())
                                .build())
                        .toList() : List.of())

                .authors(book.getAuthors() != null
                        ? book.getAuthors().stream()
                        .map(a -> AuthorDto.builder()
                                .authorId(a.getAuthor().getId())
                                .aurhorName(a.getAuthor().getName())
                                .roleId(a.getRole().getId())
                                .roleName(a.getRole().getName())
                                .build())
                        .toList() : List.of())

                .ratingByStars(book.getRatingByStars() != null
                        ? book.getRatingByStars().stream()
                        .collect(Collectors.toMap(
                                RatingByStars::getStars,
                                RatingByStars::getCount
                        ))
                        : Map.of())

                .build();
    }
}
