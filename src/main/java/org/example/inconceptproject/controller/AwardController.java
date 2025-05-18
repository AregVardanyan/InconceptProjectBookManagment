package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Award;
import org.example.inconceptproject.service.AwardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/award")
@RequiredArgsConstructor
public class AwardController {

    private final AwardService awardService;

    /**
     * Retrieve a list of all available awards.
     *
     * @return List of awards.
     */
    @GetMapping
    public List<Award> getAll() {
        return awardService.getAllAwards();
    }

    /**
     * Get details of a specific award by its ID.
     *
     * @param id The ID of the award.
     * @return Award details, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Award> getById(@PathVariable Long id) {
        return awardService.getAwardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
