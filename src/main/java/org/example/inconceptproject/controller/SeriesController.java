package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Series;
import org.example.inconceptproject.service.SeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing book series.
 */
@RestController
@RequestMapping("api/series")
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;

    /**
     * Retrieves all book series.
     *
     * @return a list of all {@link Series}
     */
    @GetMapping
    public List<Series> getAll() {
        return seriesService.getAllSeries();
    }

    /**
     * Retrieves a book series by its ID.
     *
     * @param id the ID of the series
     * @return a {@link ResponseEntity} containing the found {@link Series} or
     *         a 404 Not Found response if no series with the given ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<Series> getById(@PathVariable Long id) {
        return seriesService.getSeriesById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all distinct character names associated with a specific series.
     *
     * @param id the ID of the series
     * @return a {@link ResponseEntity} containing a list of character names
     */
    @GetMapping("/c/{id}")
    public ResponseEntity<List<String>> getAllLoreCharacters(@PathVariable Long id) {
        return ResponseEntity.ok(seriesService.getAllSeriesCharacters(id));
    }
}
