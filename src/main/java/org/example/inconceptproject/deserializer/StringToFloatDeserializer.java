package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToFloatDeserializer extends JsonDeserializer<Float> {

    @Override
    public Float deserialize(JsonParser p, DeserializationContext ctxt) {
        String value = null;

        try {
            value = p.getText();
            if (value != null) {
                Matcher m = Pattern.compile("(-?\\d+\\.?\\d*)").matcher(value);
                if (m.find()) {
                    return Float.parseFloat(m.group(1));
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
