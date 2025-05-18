package org.example.inconceptproject.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inconceptproject.model.link.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.*;


@Table(name = "book")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@JsonDeserialize
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(
            name = "book_id_seq",
            sequenceName = "book_id_seq",
            allocationSize = 50)
    private Long id;

    @Column(name = "natural_book_id")
    private Long naturalBookId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column()
    private String isbn;

    private Integer pages;

    private LocalDate publishDate;

    private LocalDate firstPublishDate;

    private Float price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "file_info_id")
    private FileInfo coverImg;

    private Integer bbeScore;

    private Integer bbeVotes;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.SUBSELECT)
    private Set<BookAuthor> authors;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<BookAward> awards;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<BookSetting> settings ;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<BookGenre> genres;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<BookCharacter> characters;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<RatingByStars> ratingByStars;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "format_id")
    private Format bookFormat;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "edition_id")
    private Edition edition;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    private Integer numRatings;

    private Integer likedPercent;

    private Float rating;

    @PrePersist
    @PreUpdate
    public void updateRatingStats() {
        long totalRatings = 0;
        long totalCount = 0;
        long likedCount = 0;
        float weightedRating = 0;

        for (RatingByStars rating : ratingByStars) {
            totalRatings += rating.getStars() * rating.getCount();
            totalCount += rating.getCount();
            if (rating.getStars() >= 3) {
                likedCount += rating.getCount();
            }
            weightedRating += rating.getStars() * rating.getCount();
        }

        if (totalCount > 0) {
            this.numRatings = (int) totalRatings;
            this.likedPercent = (int) ((likedCount * 100) / totalCount);
            this.rating = weightedRating / totalCount;
        }

        else {
            this.numRatings = 0;
            this.likedPercent = 0;
            this.rating = 0.0f;
        }
    }

    public Integer getNumRatings() {
        if (ratingByStars == null) return 0;
        return ratingByStars.stream()
                .mapToInt(r -> r.getCount() != null ? r.getCount() : 0)
                .sum();
    }

    public Integer getLikedPercent() {
        if (ratingByStars == null || ratingByStars.isEmpty()) return 0;
        int likedCount = ratingByStars.stream()
                .filter(r -> r.getStars() != null && r.getStars() >= 3)
                .mapToInt(r -> r.getCount() != null ? r.getCount() : 0)
                .sum();
        int total = getNumRatings();
        return total == 0 ? 0 : (int) Math.round((likedCount * 100.0) / total);
    }

    public Float getRating() {
        if (ratingByStars == null || ratingByStars.isEmpty()) return 0f;
        int total = getNumRatings();
        float weightedSum = (float) ratingByStars.stream()
                .filter(r -> r.getStars() != null)
                .mapToDouble(r -> r.getStars() * (r.getCount() != null ? r.getCount() : 0))
                .sum();
        return total == 0 ? 0f : (float) (Math.round((weightedSum / total) * 100) / 100.0);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return Objects.equals(naturalBookId, book.naturalBookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naturalBookId);
    }

    @Override
    public String toString() {
        return naturalBookId + "";
    }

    public Book addGenres(List<BookGenre> bookGenres){
        if (genres == null) genres = new HashSet<>();
        for (BookGenre bookGenre : bookGenres){
            addGenre(bookGenre);
        }
        return this;
    }

    public void addGenre(BookGenre genre) {
        if (genres == null) genres = new HashSet<>();
        genre.setBook(this);
        genres.add(genre);
    }

    public Book removeGenres(List<BookGenre> bookGenres) {
        for (BookGenre bookGenre : bookGenres){
            removeGenre(bookGenre);
        }
        return this;
    }

    public Book removeGenre(BookGenre genre) {
        if (genres != null) {
            genres.remove(genre);
            genre.setBook(null);
            genre.setGenre(null); // optional
        }
        return this;
    }


    public Book addCharacters(List<BookCharacter> bookCharacters) {
        if (characters == null) characters = new HashSet<>();
        for (BookCharacter bookCharacter : bookCharacters) {
            addCharacter(bookCharacter);
        }
        return this;
    }

    public void addCharacter(BookCharacter bookCharacter) {
        if (characters == null) characters = new HashSet<>();
        bookCharacter.setBook(this);
        if (bookCharacter.getCharacter() != null) {
            bookCharacter.getCharacter().setSeries(this.series);
        }
        characters.add(bookCharacter);
    }

    public Book removeCharacters(List<BookCharacter> bookCharacters) {
        for (BookCharacter bookCharacter : bookCharacters) {
            removeCharacter(bookCharacter);
        }
        return this;
    }

    public Book removeCharacter(BookCharacter character) {
        if (characters != null) {
            characters.remove(character);
            character.setBook(null);
            character.setCharacter(null); // optional
        }
        return this;
    }


    public Book addRatingByStars(List<RatingByStars> ratings) {
        if (this.ratingByStars == null) {
            this.ratingByStars = new ArrayList<>();
        }
        for (RatingByStars rating : ratings) {
            this.addRatingByStar(rating);
        }
        return this;
    }

    public void addRatingByStar(RatingByStars rating) {
        ratingByStars.add(rating);
        rating.setBook(this);
    }

    public Book addSettings(List<BookSetting> bookSettings) {
        if (settings == null) settings = new HashSet<>();
        for (BookSetting bookSetting : bookSettings) {
            addSetting(bookSetting);
        }
        return this;
    }

    public void addSetting(BookSetting bookSetting) {
        if (settings == null) settings = new HashSet<>();
        bookSetting.setBook(this);
        settings.add(bookSetting);
    }

    public Book removeSettings(List<BookSetting> bookSettings) {
        for (BookSetting bookSetting : bookSettings) {
            removeSetting(bookSetting);
        }
        return this;
    }

    public Book removeSetting(BookSetting setting) {
        if (settings != null) {
            settings.remove(setting);
            setting.setBook(null);
            setting.setSetting(null);
        }
        return this;
    }


    public Book addAuthors(List<BookAuthor> bookAuthors) {
        if (authors == null) authors = new HashSet<>();
        for (BookAuthor bookAuthor : bookAuthors) {
            addAuthor(bookAuthor);
        }
        return this;
    }

    public void addAuthor(BookAuthor bookAuthor) {
        if (authors == null) authors = new HashSet<>();
        bookAuthor.setBook(this);
        authors.add(bookAuthor);
    }

    public Book removeAuthors(List<BookAuthor> bookAuthors) {
        for (BookAuthor bookAuthor : bookAuthors) {
            removeAuthor(bookAuthor);
        }
        return this;
    }

    public Book removeAuthor(BookAuthor author) {
        if (authors != null) {
            authors.remove(author);
            author.setBook(null);
            author.setAuthor(null);
        }
        return this;
    }


    public Book addAwards(List<BookAward> bookAwards) {
        if (awards == null) awards = new HashSet<>();
        for (BookAward bookAward : bookAwards) {
            addAward(bookAward);
        }
        return this;
    }

    public void addAward(BookAward bookAward) {
        if (awards == null) awards = new HashSet<>();
        bookAward.setBook(this);
        awards.add(bookAward);
    }

    public Book removeAwards(List<BookAward> bookAwards) {
        for (BookAward bookAward : bookAwards) {
            removeAward(bookAward);
        }
        return this;
    }

    public Book removeAward(BookAward award) {
        if (awards != null) {
            awards.remove(award);
            award.setBook(null);
            award.setAward(null);
        }
        return this;
    }

}
