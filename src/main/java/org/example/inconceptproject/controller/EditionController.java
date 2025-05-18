package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Edition;
import org.example.inconceptproject.service.EditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing editions.
 */
@RestController
@RequestMapping("api/edition")
@RequiredArgsConstructor
public class EditionController {

    private final EditionService editionService;

    /**
     * Retrieves all editions.
     *
     * @return list of all {@link Edition} entities
     */
    @GetMapping
    public List<Edition> getAll() {
        return editionService.getAllEditions();
    }

    /**
     * Retrieves a specific edition by its ID.
     *
     * @param id the ID of the edition to retrieve
     * @return a {@link ResponseEntity} containing the {@link Edition} if found,
     *         or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Edition> getById(@PathVariable Long id) {
        return editionService.getEditionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new edition.
     * <p>
     * Requires user to have ADMIN or MODERATOR role.
     *
     * @param edition the edition data to create
     * @return the created {@link Edition}
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public Edition create(@RequestBody Edition edition) {
        return editionService.saveEdition(edition);
    }

    /**
     * Updates an existing edition by ID.
     * <p>
     * Requires user to have ADMIN or MODERATOR role.
     *
     * @param id the ID of the edition to update
     * @param edition the updated edition data
     * @return the updated {@link Edition}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Edition update(@PathVariable Long id, @RequestBody Edition edition) {
        return editionService.updateEdition(id, edition);
    }

    /**
     * Deletes an edition by its ID.
     *
     * @param id the ID of the edition to delete
     * @return a {@link ResponseEntity} with HTTP status 200 OK if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        editionService.deleteEdition(id);
        return ResponseEntity.ok().build();
    }
}
