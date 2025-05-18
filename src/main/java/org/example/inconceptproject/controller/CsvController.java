package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Book;
import org.example.inconceptproject.service.csvrelated.CSVToDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Queue;

/**
 * REST controller for uploading CSV files containing book data.
 */
@RestController
@RequestMapping("api/upload")
@RequiredArgsConstructor
public class CsvController {

    private final CSVToDtoService csvToDtoService;

    /**
     * Uploads a CSV file containing book records and returns the number of successfully parsed books.
     * <p>
     * Only users with the role ADMIN or MODERATOR are authorized to use this endpoint.
     *
     * @param file the uploaded CSV file containing book data
     * @return a {@link ResponseEntity} containing the number of books parsed from the file
     * @throws Exception if the file processing fails
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Integer> uploadBooksCSV(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            Queue<Book> books = csvToDtoService.uploadBooksCSV(file);
            return ResponseEntity.ok(books.size());
        } catch (Exception e) {
            throw e;
        }
    }
}
