package org.example.inconceptproject.dto.request;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.*;
import org.example.inconceptproject.deserializer.*;
import org.example.inconceptproject.model.*;
import org.example.inconceptproject.model.link.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCsvDTO {

    @JsonProperty("bookId")
    @JsonDeserialize(using = BookIdDeserializer.class)
    private Long bookId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("series")
    @JsonDeserialize(using = SeriesDeserializer.class)
    private Series series;

    @JsonProperty("author")
    @JsonDeserialize(using = AuthorsDeserializer.class)
    private List<BookAuthor> author;

    @JsonIgnore
    private Float rating;

    @JsonProperty("description")
    private String description;

    @JsonProperty("language")
    @JsonDeserialize(using = LanguageDeserializer.class)
    private Language language;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("genres")
    @JsonDeserialize(using = GenresDeserializer.class)
    private List<BookGenre> genres;

    @JsonProperty("characters")
    @JsonDeserialize(using = CharactersDeserializer.class)
    private List<BookCharacter> characters;

    @JsonProperty("bookFormat")
    @JsonDeserialize(using = FormatDeserializer.class)
    private Format bookFormat;

    @JsonProperty("edition")
    @JsonDeserialize(using = EditionDeserializer.class)
    private Edition edition;

    @JsonProperty("pages")
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer pages;

    @JsonProperty("publisher")
    @JsonDeserialize(using = PublisherDeserializer.class)
    private Publisher publisher;

    @JsonProperty("publishDate")
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate publishDate;

    @JsonProperty("firstPublishDate")
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate firstPublishDate;

    @JsonProperty("awards")
    @JsonDeserialize(using = AwardsDeserializer.class)
    private List<BookAward> awards;

    @JsonIgnore
    private Integer numRatings;

    @JsonProperty("ratingsByStars")
    @JsonDeserialize(using = RatingsByStarsDeserializer.class)
    private List<RatingByStars> ratingsByStars;

    @JsonIgnore
    private Integer likedPercent;

    @JsonProperty("setting")
    @JsonDeserialize(using = SettingsDeserializer.class)
    private List<BookSetting> setting;

    @JsonProperty("coverImg")
    @JsonDeserialize(using = CoverImageDeserializer.class)
    private FileInfo coverImg;

    @JsonProperty("bbeScore")
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer bbeScore;

    @JsonProperty("bbeVotes")
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer bbeVotes;

    @JsonProperty("price")
    @JsonDeserialize(using = StringToFloatDeserializer.class)
    private Float  price;

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }

    public Book mapToBook(){

        Book book = Book.builder()
                .naturalBookId(this.getBookId())
                .title(this.getTitle())
                .series(this.getSeries())
                .description(this.getDescription())
                .isbn(this.getIsbn().equals("9999999999999") ? null : this.getIsbn())
                .pages(this.getPages())
                .publishDate(this.getPublishDate())
                .firstPublishDate(this.getFirstPublishDate())
                .price(this.getPrice())
                .coverImg(this.getCoverImg())
                .bbeScore(this.getBbeScore())
                .bbeVotes(this.getBbeVotes())
                .edition(this.getEdition())
                .language(this.getLanguage())
                .bookFormat(this.getBookFormat())
                .publisher(this.getPublisher())
                .build();
        book.addGenres(this.genres);
        book.addAuthors(this.author);
        book.addAwards(this.awards);
        book.addCharacters(this.characters);
        book.addSettings(this.setting);
        book.addRatingByStars(this.ratingsByStars);
        return book;
    }
}
