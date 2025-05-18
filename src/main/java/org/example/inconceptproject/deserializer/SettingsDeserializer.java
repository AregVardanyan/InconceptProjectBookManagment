package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.inconceptproject.model.Setting;
import org.example.inconceptproject.model.link.BookSetting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettingsDeserializer extends JsonDeserializer<List<BookSetting>> {

    @Override
    public List<BookSetting> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Set<BookSetting> normalizedSettings = new HashSet<>();
        String input = p.getText();

        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>(normalizedSettings);
        }

        input = input.trim();
        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1).trim();
        }
        input = input.replaceAll("'", "");
        String[] settings = input.split(",");

        for (String setting : settings) {
            setting = setting.trim().replaceAll("^\"|\"$", "");
            Setting newSetting = Setting.builder().name(setting).build();
            BookSetting bookSetting = BookSetting.builder().setting(newSetting).build();
            normalizedSettings.add(bookSetting);
        }

        return new ArrayList<>(normalizedSettings);
    }
}
