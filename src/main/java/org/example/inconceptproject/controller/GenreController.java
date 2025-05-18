package org.example.inconceptproject.controller;

import org.example.inconceptproject.model.Genre;
import org.example.inconceptproject.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing genres.
 */
@RestController
@RequestMapping("/api/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;

    /**
     * Retrieves the top genres based on the book number.
     *
     * @param top the maximum number of genres to return
     * @return a list of top {@link Genre} entities
     */
    @GetMapping
    public List<Genre> getAllGenres(@RequestParam Integer top) {
        return genreService.getAllGenres(top);
    }

    /**
     * Retrieves a genre by its ID.
     *
     * @param id the ID of the genre to retrieve
     * @return a {@link ResponseEntity} containing the {@link Genre} if found,
     *         or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return genreService.getGenreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
