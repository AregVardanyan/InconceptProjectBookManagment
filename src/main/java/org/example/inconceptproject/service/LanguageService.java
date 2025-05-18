package org.example.inconceptproject.service;

import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Language;
import org.example.inconceptproject.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Optional<Language> getLanguageById(Long id) {
        return languageRepository.findById(id);
    }

    public Language saveLanguage(Language language) {
        return languageRepository.save(language);
    }

    public Language updateLanguage(Long id, Language updated) {
        return languageRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    return languageRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Language not found"));
    }



    public void loadLanguages(Set<BookCsvDTO> books) {
        Map<Language, Language> languageMap = languageRepository.findAll().stream()
                .collect(Collectors.toMap(language -> language, Function.identity()));

        for (BookCsvDTO book : books) {
            if (book.getLanguage() == null){continue;}

            if(!languageMap.containsKey(book.getLanguage())) {
                languageMap.put(book.getLanguage(), book.getLanguage());
            }
            book.setLanguage(languageMap.get(book.getLanguage()));
        }
    }
}
