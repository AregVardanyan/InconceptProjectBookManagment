package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.inconceptproject.model.Award;
import org.example.inconceptproject.model.link.BookAward;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AwardsDeserializer extends JsonDeserializer<List<BookAward>> {

    @Override
    public List<BookAward> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Set<BookAward> normalizedAwards = new HashSet<>();
        String input = p.getText();

        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>(normalizedAwards);
        }

        input = input.trim();
        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1).trim();
        }

        String[] awards = input.split(",");
        Pattern pattern = Pattern.compile("([^\\(]+)\\s*(\\(\\d{4}\\))?");

        for (String award : awards) {
            Matcher matcher = pattern.matcher(award.trim());

            if (matcher.find()) {
                String awardName = matcher.group(1).trim();
                String yearStr = matcher.group(2) != null ? matcher.group(2).replaceAll("[()]", "") : null;

                Award newAward = Award.builder().name(awardName).build();
                Long year = (yearStr != null && !yearStr.isEmpty()) ? Long.parseLong(yearStr) : null;

                BookAward bookAward = BookAward.builder().award(newAward).year(year).build();
                normalizedAwards.add(bookAward);
            }
        }
        return new ArrayList<>(normalizedAwards);
    }
}
