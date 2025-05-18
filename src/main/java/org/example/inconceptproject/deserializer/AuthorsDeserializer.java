package org.example.inconceptproject.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.inconceptproject.model.Author;
import org.example.inconceptproject.model.AuthorRole;
import org.example.inconceptproject.model.link.BookAuthor;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorsDeserializer extends JsonDeserializer<List<BookAuthor>> {

    private static final String AUTHOR_ROLE_PATTERN = "(.+?)(?:\\s*\\((.*?)\\))?$";

    @Override
    public List<BookAuthor> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String input = p.getText();
        Set<BookAuthor> normalizedAuthors = new HashSet<>();

        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>(normalizedAuthors);
        }

        String[] authorList = input.split(",");

        for (String author : authorList) {
            author = author.trim();

            Pattern pattern = Pattern.compile(AUTHOR_ROLE_PATTERN);
            Matcher matcher = pattern.matcher(author);

            if (matcher.matches()) {
                String authorName = matcher.group(1).trim();
                String rolesString = matcher.group(2);
                Author newAuthor = Author.builder().name(authorName).build();

                if (rolesString != null && !rolesString.isEmpty()) {
                    String[] roles = rolesString.split("\\)\\s*\\(");

                    roles[0] = roles[0].replace("(", "").trim();
                    roles[roles.length - 1] = roles[roles.length - 1].replace(")", "").trim();

                    for (String role : roles) {
                        AuthorRole newRole = AuthorRole.builder().name(role.trim()).build();
                        BookAuthor bookAuthor = BookAuthor.builder().author(newAuthor).role(newRole).build();
                        normalizedAuthors.add(bookAuthor);
                    }
                } else {
                    AuthorRole newRole = AuthorRole.builder().name("Author").build();
                    BookAuthor bookAuthor = BookAuthor.builder().author(newAuthor).role(newRole).build();

                    normalizedAuthors.add(bookAuthor);
                }
            } else {
                Author newAuthor = Author.builder().name(author).build();
                AuthorRole newAuthorRole = AuthorRole.builder().name("Author").build();
                BookAuthor bookAuthor = BookAuthor.builder().author(newAuthor).role(newAuthorRole).build();

                normalizedAuthors.add(bookAuthor);
            }
        }

        return new ArrayList<>(normalizedAuthors);
    }
}
