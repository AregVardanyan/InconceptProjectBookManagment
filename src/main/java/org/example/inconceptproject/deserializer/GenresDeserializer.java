package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Genre;
import org.example.inconceptproject.model.link.BookGenre;
import org.example.inconceptproject.repository.GenreRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GenresDeserializer extends JsonDeserializer<List<BookGenre>> {


    @Override
    public List<BookGenre> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getText() == null || p.getText().isBlank() || p.getText().isEmpty()) {
            return new ArrayList<>();
        }
        Set<BookGenre> normalizedGenres = new HashSet<>();
        String input = p.getText();

        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        input = input.trim();
        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1).trim();
        }

        input = input.replaceAll("'", "");
        String[] genres = input.split(",");

        for (String genre : genres) {
            genre = genre.trim().replaceAll("^\"|\"$", "");

            Genre newGenre = Genre.builder().name(genre).build();
            BookGenre bookGenre = BookGenre.builder().genre(newGenre).build();
            normalizedGenres.add(bookGenre);
        }

        return new ArrayList<>(normalizedGenres);
    }
}
