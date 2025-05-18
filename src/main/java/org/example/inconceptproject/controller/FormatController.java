package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Format;
import org.example.inconceptproject.service.FormatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing formats.
 */
@RestController
@RequestMapping("api/format")
@RequiredArgsConstructor
public class FormatController {

    private final FormatService formatService;

    /**
     * Retrieves all formats.
     *
     * @return list of all {@link Format} entities
     */
    @GetMapping
    public List<Format> getAll() {
        return formatService.getAllFormats();
    }

    /**
     * Retrieves a specific format by its ID.
     *
     * @param id the ID of the format to retrieve
     * @return a {@link ResponseEntity} containing the {@link Format} if found,
     *         or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Format> getById(@PathVariable Long id) {
        return formatService.getFormatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new format.
     * <p>
     * Requires user to have ADMIN or MODERATOR role.
     *
     * @param format the format data to create
     * @return the created {@link Format}
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Format create(@RequestBody Format format) {
        return formatService.saveFormat(format);
    }

    /**
     * Updates an existing format by ID.
     * <p>
     * Requires user to have ADMIN or MODERATOR role.
     *
     * @param id the ID of the format to update
     * @param format the updated format data
     * @return a {@link ResponseEntity} containing the updated {@link Format} if successful,
     *         or a 404 Not Found response if the format with the specified ID does not exist
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Format> update(@PathVariable Long id, @RequestBody Format format) {
        try {
            return ResponseEntity.ok(formatService.updateFormat(id, format));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a format by its ID.
     *
     * @param id the ID of the format to delete
     * @return a {@link ResponseEntity} with HTTP status 204 No Content if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formatService.deleteFormat(id);
        return ResponseEntity.noContent().build();
    }
}
