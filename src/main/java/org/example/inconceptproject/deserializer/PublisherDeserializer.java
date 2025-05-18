package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Edition;
import org.example.inconceptproject.model.Publisher;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class PublisherDeserializer extends JsonDeserializer<Publisher> {

    @Override
    public Publisher deserialize(JsonParser p, DeserializationContext cntx) throws IOException, JacksonException {
        if( p.getText() == null || p.getText().isBlank() || p.getText().isEmpty()){
            return null;
        }
        return Publisher.builder().name(p.getText()).build();
    }
}