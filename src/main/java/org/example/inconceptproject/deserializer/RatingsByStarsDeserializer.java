package org.example.inconceptproject.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.inconceptproject.model.RatingByStars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RatingsByStarsDeserializer extends JsonDeserializer<List<RatingByStars>> {

    @Override
    public List<RatingByStars> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String input = p.getText();
        List<RatingByStars> ratingByStarsList = new ArrayList<>();

        if (input == null || input.trim().isEmpty()) {
            return ratingByStarsList;
        }

        input = input.trim();
        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1).trim();
        }

        if (input.isEmpty()) return ratingByStarsList;

        input = input.replace("'", "");
        String[] parts = input.split(",");

        if(parts.length != 5){return ratingByStarsList; }

        for (int i = 0; i < 5; i++) {
            int star = 5-i;
            int count = 0;

            try {
                count = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException ignored) {
                return new ArrayList<>();}

            RatingByStars rating = RatingByStars.builder()
                    .stars(star)
                    .count(count)
                    .build();
            ratingByStarsList.add(rating);
        }

        return ratingByStarsList;
    }
}

