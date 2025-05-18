package org.example.inconceptproject.service;

import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Edition;
import org.example.inconceptproject.repository.EditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EditionService {
    @Autowired
    private EditionRepository editionRepository;


    public List<Edition> getAllEditions() {
        return editionRepository.findAll();
    }

    public Optional<Edition> getEditionById(Long id) {
        return editionRepository.findById(id);
    }

    public Edition saveEdition(Edition edition) {
        return editionRepository.save(edition);
    }

    public Edition updateEdition(Long id, Edition updated) {
        return editionRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    return editionRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Edition not found"));
    }

    public void deleteEdition(Long id) {
        editionRepository.nullifyEditionInBooks(id);
        editionRepository.deleteById(id);
    }

    public void loadEditions(Set<BookCsvDTO> books) {
        Map<Edition, Edition> editionMap = editionRepository.findAll().stream()
                .collect(Collectors.toMap(edition -> edition, Function.identity()));

        for (BookCsvDTO book : books) {
            if (book.getEdition() == null){continue;}

            if(!editionMap.containsKey(book.getEdition())) {
                editionMap.put(book.getEdition(), book.getEdition());
            }
            book.setEdition(editionMap.get(book.getEdition()));
        }
    }
}
