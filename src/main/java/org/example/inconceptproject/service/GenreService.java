package org.example.inconceptproject.service;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Genre;
import org.example.inconceptproject.model.link.BookCharacter;
import org.example.inconceptproject.model.link.BookGenre;
import org.example.inconceptproject.repository.BookRepository;
import org.example.inconceptproject.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;


    public void loadGenres(Set<BookCsvDTO> books) {
        Map<Genre, Genre> genreMap = genreRepository.findAll().stream()
                .collect(Collectors.toMap(genre -> genre, Function.identity()));


        for (BookCsvDTO book : books) {
            List<BookGenre> genres = book.getGenres();

            for (BookGenre genre : genres) {
                if (genre.getGenre() == null) continue;

                if(!genreMap.containsKey(genre.getGenre())){
                    genreMap.put(genre.getGenre(), genre.getGenre());
                }
                genre.setGenre(genreMap.get(genre.getGenre()));
            }
        }
    }
    public List<Genre> getAllGenres(Integer top) {
        if(top == null) return genreRepository.findAll();

        return genreRepository.findTopByBookCount(top);
    }
    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }
}
