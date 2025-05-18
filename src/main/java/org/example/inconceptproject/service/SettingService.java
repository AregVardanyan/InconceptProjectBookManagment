package org.example.inconceptproject.service;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Setting;
import org.example.inconceptproject.model.link.BookGenre;
import org.example.inconceptproject.model.link.BookSetting;
import org.example.inconceptproject.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    public void loadSettings(Set<BookCsvDTO> books) {
        Map<Setting, Setting> settingMap = settingRepository.findAll().stream()
                .collect(Collectors.toMap(setting -> setting, Function.identity()));

        for (BookCsvDTO book : books) {
            List<BookSetting> settings = book.getSetting();

            for (BookSetting setting : settings) {
                if (setting.getSetting() == null) continue;

                if(!settingMap.containsKey(setting.getSetting())){
                    settingMap.put(setting.getSetting(), setting.getSetting());
                }
                setting.setSetting(settingMap.get(setting.getSetting()));
            }
        }
    }

    public List<Setting> getAllSettings() {
        return settingRepository.findAll();
    }

    public Optional<Setting> getSettingById(Long id) {
        return settingRepository.findById(id);
    }


}

