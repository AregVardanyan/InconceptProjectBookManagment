package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.criteria.AuthorSearchCriteria;
import org.example.inconceptproject.dto.response.AuthorStatisticsDTO;
import org.example.inconceptproject.model.Author;
import org.example.inconceptproject.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Search for authors based on given criteria.
     * Returns a list of authors with associated statistics (e.g., book count, ratings).
     *
     * @param criteria Search filters like name, nationality, etc.
     * @return List of authors with statistical information.
     */
    @GetMapping
    public List<AuthorStatisticsDTO> searchAuthors(AuthorSearchCriteria criteria) {
        return authorService.getAllAuthors(criteria);
    }

    /**
     * Get detailed information about a specific author by their ID.
     *
     * @param id The ID of the author.
     * @return The author object, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
