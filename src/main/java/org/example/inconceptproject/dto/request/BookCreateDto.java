package org.example.inconceptproject.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import org.example.inconceptproject.deserializer.*;
import org.example.inconceptproject.model.*;
import org.example.inconceptproject.model.link.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BookCreateDto {
    private Long naturalBookId;
    private String title;

    @JsonDeserialize(using = SeriesDeserializer.class)
    private Series series;


    private String description;

    private String isbn;
    private Integer pages;

    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate firstPublishDate;

    private Float price;

    @JsonDeserialize(using = CoverImageDeserializer.class)
    private FileInfo coverImg;

    private Integer bbeScore;
    private Integer bbeVotes;

    @JsonDeserialize(using = LanguageDeserializer.class)
    private Language language;

    @JsonDeserialize(using = FormatDeserializer.class)
    private Format format;

    @JsonDeserialize(using = EditionDeserializer.class)
    private Edition edition;

    @JsonDeserialize(using = PublisherDeserializer.class)
    private Publisher publisher;

    @JsonDeserialize(using = GenresDeserializer.class)
    private List<BookGenre> genres;

    @JsonDeserialize(using = SettingsDeserializer.class)
    private List<BookSetting> settings;

    @JsonDeserialize(using = CharactersDeserializer.class)
    private List<BookCharacter> characters;

    @JsonDeserialize(using = AuthorsDeserializer.class)
    private List<BookAuthor> authors;

    @JsonDeserialize(using = AwardsDeserializer.class)
    private List<BookAward> awards;

    public Book toBook(){
        List<RatingByStars> ratingByStars = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ratingByStars.add(RatingByStars.builder()
                    .stars(i + 1)
                    .count(0)
                    .build());
        }

        return Book.builder()
                .naturalBookId(naturalBookId)
                .title(title)
                .series(series)
                .description(description)
                .isbn(isbn)
                .pages(pages)
                .publishDate(LocalDate.now())
                .firstPublishDate(firstPublishDate)
                .price(price)
                .coverImg(coverImg)
                .bbeScore(bbeScore)
                .bbeVotes(bbeVotes)
                .language(language)
                .bookFormat(format)
                .edition(edition)
                .publisher(publisher)
                .build()
                .addGenres(genres)
                .addAwards(awards)
                .addSettings(settings)
                .addCharacters(characters)
                .addAuthors(authors)
                .addRatingByStars(ratingByStars);
    }
}
