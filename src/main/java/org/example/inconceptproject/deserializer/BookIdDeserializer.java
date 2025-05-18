package org.example.inconceptproject.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class BookIdDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        String raw = p.getText();
        if (raw == null || raw.isEmpty()) return null;

        String[] split = raw.split("[.-]", 2);
        try {
            return Long.parseLong(split[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid bookId: " + raw);
            return null;
        }
    }
}
