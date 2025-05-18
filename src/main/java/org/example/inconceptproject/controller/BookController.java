package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.criteria.BookSearchCriteria;
import org.example.inconceptproject.dto.request.BookCreateDto;
import org.example.inconceptproject.dto.request.BookUpdateDto;
import org.example.inconceptproject.dto.response.BookInfoDTO;
import org.example.inconceptproject.dto.response.BookLessInfoDTO;
import org.example.inconceptproject.dto.response.PageResponseDto;
import org.example.inconceptproject.service.BookService;
import org.example.inconceptproject.service.csvrelated.CSVToDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling book-related API endpoints.
 */
@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CSVToDtoService csvToDtoService;

    /**
     * Retrieves a paginated list of books based on search criteria.
     *
     * @param criteria the criteria to filter/search books
     * @return a page of books with limited information
     */
    @GetMapping
    public PageResponseDto<BookLessInfoDTO> searchBooks(BookSearchCriteria criteria) {
        return bookService.getAll(criteria);
    }

    /**
     * Retrieves full book information by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return a {@link ResponseEntity} containing the book information if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookInfoDTO> getBookById(@PathVariable Long id) {
        BookInfoDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Creates a new book.
     * Only accessible by users with ADMIN or MODERATOR roles.
     *
     * @param bookDto the book creation data
     * @return the created book's full information
     * @throws Exception if an error occurs during creation
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<BookInfoDTO> createBook(@RequestBody BookCreateDto bookDto) throws Exception {
        BookInfoDTO book = bookService.uploadBook(bookDto);
        return ResponseEntity.ok(book);
    }

    /**
     * Updates an existing book by its ID.
     * Only accessible by users with ADMIN or MODERATOR roles.
     *
     * @param dto the book update data
     * @param id  the ID of the book to update
     * @return the updated book's full information
     * @throws Exception if the book is not found or update fails
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<BookInfoDTO> updateBook(@RequestBody BookUpdateDto dto, @PathVariable Long id) throws Exception {
        BookInfoDTO bookInfoDTO = bookService.updateBook(dto, id);
        return ResponseEntity.ok(bookInfoDTO);
    }
}
