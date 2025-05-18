package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.model.Edition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.JsonDeserializer;



public class EditionDeserializer extends JsonDeserializer<Edition>{

    @Override
    public Edition deserialize(JsonParser p, DeserializationContext cntx) throws IOException, JacksonException {
        if( p.getText() == null || p.getText().isBlank() || p.getText().isEmpty()){
            return null;
        }
        return Edition.builder().name(p.getText()).build();
    }
}
