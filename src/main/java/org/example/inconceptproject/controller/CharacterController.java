package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing character-related operations.
 */
@RestController
@RequestMapping("api/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    /**
     * Retrieves a list of all characters.
     *
     * @return a list of {@link Characterr} objects
     */
    @GetMapping
    public List<Characterr> getAll() {
        return characterService.getAllCharacters();
    }

    /**
     * Retrieves a character by its ID.
     *
     * @param id the ID of the character
     * @return a {@link ResponseEntity} with the character if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Characterr> getById(@PathVariable Long id) {
        return characterService.getCharacterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
