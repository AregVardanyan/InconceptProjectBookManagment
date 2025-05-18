package org.example.inconceptproject.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.example.inconceptproject.model.*;
import org.example.inconceptproject.model.Characterr;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class BookLessInfoDTO{

    private Long id;
    private String title;
    private String series;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private Float rating;

    private String coverImg;

    private List<Map.Entry<String, String>> authors;

    public static BookLessInfoDTO toDto(Book book) {
        return BookLessInfoDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .series(book.getSeries().getName())
                .rating(book.getRating())
                .coverImg(book.getCoverImg() != null ? book.getCoverImg().getFilePath() : null)
                .authors(book.getAuthors() != null
                        ? book.getAuthors().stream()
                        .map(a -> new AbstractMap.SimpleEntry<>(a.getAuthor().getName(), a.getRole().getName()))
                        .collect(Collectors.toList())
                        : List.of())
                .build();
    }
}

