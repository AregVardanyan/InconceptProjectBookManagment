package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.example.inconceptproject.model.Publisher;
import org.example.inconceptproject.model.Series;

import java.io.IOException;

public class SeriesDeserializer extends JsonDeserializer<Series> {
    @Override
    public Series deserialize(JsonParser p, DeserializationContext cntx) throws IOException, JacksonException {
        return Series.builder().name(p.getText()).build();
    }
}
