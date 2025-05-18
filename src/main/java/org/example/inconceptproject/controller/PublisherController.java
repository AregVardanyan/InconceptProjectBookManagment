package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Publisher;
import org.example.inconceptproject.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing publishers.
 */
@RestController
@RequestMapping("/api/publisher")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    /**
     * Retrieves all publishers.
     *
     * @return a list of all {@link Publisher} entities
     */
    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    /**
     * Retrieves a publisher by its ID.
     *
     * @param id the ID of the publisher to retrieve
     * @return a {@link ResponseEntity} containing the {@link Publisher} if found,
     *         or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        Optional<Publisher> publisher = publisherService.getPublisherById(id);
        return publisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
