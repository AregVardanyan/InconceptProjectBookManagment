package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.link.BookCharacter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CharactersDeserializer extends JsonDeserializer<List<BookCharacter>> {

    @Override
    public List<BookCharacter> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Set<BookCharacter> normalizedCharacters = new HashSet<>();
        String input = p.getText();

        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>(normalizedCharacters);
        }

        input = input.trim();
        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1).trim();
        }
        input = input.replaceAll("'", "");
        String[] characters = input.split(",");

        for (String character : characters) {
            character = character.trim().replaceAll("^\"|\"$", "");
            Characterr newCharacter = Characterr.builder().name(character).build();
            BookCharacter bookCharacter = BookCharacter.builder().character(newCharacter).build();

            normalizedCharacters.add(bookCharacter);
        }

        return new ArrayList<>(normalizedCharacters);
    }
}
