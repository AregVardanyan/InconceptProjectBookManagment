package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Language;
import org.example.inconceptproject.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing languages.
 */
@RestController
@RequestMapping("api/language")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    /**
     * Retrieves all languages.
     *
     * @return a list of all {@link Language} entities
     */
    @GetMapping
    public List<Language> getAll() {
        return languageService.getAllLanguages();
    }

    /**
     * Retrieves a language by its ID.
     *
     * @param id the ID of the language to retrieve
     * @return a {@link ResponseEntity} containing the {@link Language} if found,
     *         or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Language> getById(@PathVariable Long id) {
        return languageService.getLanguageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new language.
     * Requires ADMIN or MODERATOR role.
     *
     * @param language the {@link Language} to create
     * @return the created {@link Language} entity
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Language create(@RequestBody Language language) {
        return languageService.saveLanguage(language);
    }

    /**
     * Updates an existing language by ID.
     *
     * @param id the ID of the language to update
     * @param language the updated {@link Language} data
     * @return the updated {@link Language} entity
     */
    @PutMapping("/{id}")
    public Language update(@PathVariable Long id, @RequestBody Language language) {
        return languageService.updateLanguage(id, language);
    }
}
