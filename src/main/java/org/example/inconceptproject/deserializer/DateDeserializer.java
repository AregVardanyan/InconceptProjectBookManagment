package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DateDeserializer extends JsonDeserializer<LocalDate> {

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("MM/dd/yy"),
            DateTimeFormatter.ofPattern("M/d/yy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("d-MMM-yyyy"),
            DateTimeFormatter.ofPattern("dd MMM yyyy"),
            DateTimeFormatter.ofPattern("d MMM yyyy"),

            DateTimeFormatter.ofPattern("MMMM d yyyy"),
            DateTimeFormatter.ofPattern("MMMM d, yyyy"),
            DateTimeFormatter.ofPattern("MMMM dd, yyyy"),
            DateTimeFormatter.ofPattern("MMMM dd yyyy"),

            DateTimeFormatter.ofPattern("MMM d yyyy"),
            DateTimeFormatter.ofPattern("MMM d, yyyy"),
            DateTimeFormatter.ofPattern("MMM dd, yyyy"),
            DateTimeFormatter.ofPattern("d MMM, yyyy"),

            DateTimeFormatter.ofPattern("MMMM yyyy"),
            DateTimeFormatter.ofPattern("MMM yyyy"),

            DateTimeFormatter.ofPattern("yyyy"),

            DateTimeFormatter.ISO_LOCAL_DATE
    );

    private static String removeOrdinalSuffix(String input) {
        return input.replaceAll("(?<=\\d)(st|nd|rd|th)", "");
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String rawDate = p.getText();

        if (rawDate == null) return null;

        rawDate = rawDate.trim();

        if (rawDate.equalsIgnoreCase("nan") || rawDate.equalsIgnoreCase("Published") || rawDate.isEmpty()) {
            return null;
        }

        String cleanedDate = removeOrdinalSuffix(rawDate);

        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(cleanedDate, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        return null;
    }
}
