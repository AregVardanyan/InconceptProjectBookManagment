package org.example.inconceptproject.service;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.link.BookCharacter;
import org.example.inconceptproject.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.inconceptproject.model.Characterr;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterService {


    private final CharacterRepository characterRepository;

    public void loadCharacters(Set<BookCsvDTO> books) {
        Map<Characterr, Characterr> characterMap = characterRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        Function.identity(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));


        for (BookCsvDTO book : books) {
            List<BookCharacter> characters = book.getCharacters();

            for (BookCharacter character : characters) {
                if (character.getCharacter() == null || character.getCharacter().getName().isBlank()) continue;

                character.getCharacter().setSeries(book.getSeries());

                if(!characterMap.containsKey(character.getCharacter())){
                    characterMap.put(character.getCharacter(), character.getCharacter());
                }
                character.setCharacter(characterMap.get(character.getCharacter()));
            }
        }
    }

    public List<Characterr> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<Characterr> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

}
