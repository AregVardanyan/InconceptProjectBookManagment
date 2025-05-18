package org.example.inconceptproject.deserializer;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.inconceptproject.model.Format;
import org.example.inconceptproject.model.Publisher;

import java.io.IOException;

public class FormatDeserializer extends JsonDeserializer<Format> {
    @Override
    public Format deserialize(JsonParser p, DeserializationContext cntx) throws IOException, JacksonException {
        if( p.getText() == null || p.getText().isBlank() || p.getText().isEmpty()){
            return null;
        }
        return Format.builder().name(p.getText()).build();
    }
}
