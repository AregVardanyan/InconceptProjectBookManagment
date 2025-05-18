package org.example.inconceptproject.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.deserializer.*;
import org.example.inconceptproject.model.*;
import org.example.inconceptproject.model.link.*;
import org.example.inconceptproject.repository.GenreRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BookUpdateDto {

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

    @JsonDeserialize(using = LanguageDeserializer.class)
    private Language language;

    @JsonDeserialize(using = FormatDeserializer.class)
    private Format format;

    @JsonDeserialize(using = EditionDeserializer.class)
    private Edition edition;

    @JsonDeserialize(using = PublisherDeserializer.class)
    private Publisher publisher;

    @JsonDeserialize(using = GenresDeserializer.class)
    private List<BookGenre> addGenres;

    private List<Long> removeGenres;

    @JsonDeserialize(using = SettingsDeserializer.class)
    private List<BookSetting> addSettings;

    private List<Long> removeSettings;

    @JsonDeserialize(using = CharactersDeserializer.class)
    private List<BookCharacter> addCharacters;

    private List<Long> removeCharacters;

    @JsonDeserialize(using = AuthorsDeserializer.class)
    private List<BookAuthor> addAuthors;

    private List<Long> removeAuthors;

    @JsonDeserialize(using = AwardsDeserializer.class)
    private List<BookAward> addAwards;

    private List<Long> removeAwards;

}
