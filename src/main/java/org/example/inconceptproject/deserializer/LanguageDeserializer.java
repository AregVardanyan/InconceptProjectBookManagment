package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Language;
import org.example.inconceptproject.model.Publisher;
import org.example.inconceptproject.service.LanguageService;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class LanguageDeserializer extends JsonDeserializer<Language> {

    @Override
    public Language deserialize(JsonParser p, DeserializationContext cntx) throws IOException, JacksonException {
        if( p.getText() == null || p.getText().isBlank() || p.getText().isEmpty()){
            return null;
        }
        return Language.builder().name(p.getText()).build();
    }
}
