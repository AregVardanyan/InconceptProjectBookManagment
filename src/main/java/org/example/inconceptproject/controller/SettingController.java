package org.example.inconceptproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Setting;
import org.example.inconceptproject.service.SettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing application settings.
 */
@RestController
@RequestMapping("api/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    /**
     * Retrieves all settings.
     *
     * @return a list of all {@link Setting} objects
     */
    @GetMapping
    public List<Setting> getAll() {
        return settingService.getAllSettings();
    }

    /**
     * Retrieves a setting by its ID.
     *
     * @param id the ID of the setting
     * @return a {@link ResponseEntity} containing the found {@link Setting} or
     *         a 404 Not Found response if no setting with the given ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<Setting> getById(@PathVariable Long id) {
        return settingService.getSettingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
