package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.response.BookInfoDTO;
import org.example.inconceptproject.service.BookService;
import org.example.inconceptproject.service.RatingsByStarsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing book ratings and BBE (Best Book Ever) scores.
 */
@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingsByStarsService ratingByStarsService;
    private final BookService bookService;

    /**
     * Adds a star rating to a book.
     *
     * @param bookId the ID of the book to rate
     * @param stars  the star rating value to add
     * @return a {@link ResponseEntity} with status 201 Created on success,
     *         or 400 Bad Request if the input is invalid
     */
    @PostMapping()
    public ResponseEntity<Void> addRating(@RequestParam Long bookId, @RequestParam Integer stars) {
        try {
            ratingByStarsService.addStar(bookId, stars);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Adds or updates the BBE (Best Book Ever) score and vote count for a book.
     * Accessible only to users with roles ADMIN, MODERATOR, or CRITIC.
     *
     * @param id       the ID of the book to update
     * @param bbeScore the BBE score to set
     * @param bbeVotes the number of votes for the BBE score
     * @return a {@link ResponseEntity} containing the updated {@link BookInfoDTO}
     */
    @PostMapping("/bbe/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'CRITIC')")
    private ResponseEntity<BookInfoDTO> addBbe(@PathVariable Long id,
                                               @RequestParam Integer bbeScore,
                                               @RequestParam Integer bbeVotes) {
        BookInfoDTO bookInfoDTO = bookService.updateBbe(id, bbeScore, bbeVotes);
        return ResponseEntity.ok(bookInfoDTO);
    }
}
