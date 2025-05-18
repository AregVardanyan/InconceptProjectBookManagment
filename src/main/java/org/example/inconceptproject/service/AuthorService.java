package org.example.inconceptproject.service;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.criteria.AuthorSearchCriteria;
import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.dto.response.AuthorStatisticsDTO;
import org.example.inconceptproject.model.*;
import org.example.inconceptproject.model.link.BookAuthor;
import org.example.inconceptproject.repository.AuthorRepository;
import org.example.inconceptproject.repository.AuthorRoleRepository;

import org.example.inconceptproject.repository.link.BookAuthorRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorRoleRepository roleRepository;
    private final BookAuthorRepository bookAuthorRepository;


    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void loadAuthors(Set<BookCsvDTO> books) {
        Map<Author, Author> authorMap = authorRepository.findAll().stream()
                .collect(Collectors.toMap(author -> author, Function.identity()));

        Map<AuthorRole, AuthorRole> roleMap = roleRepository.findAll().stream()
                .collect(Collectors.toMap(role -> role, Function.identity()));


        for (BookCsvDTO book : books) {
            List<BookAuthor> authors = book.getAuthor();
            for (BookAuthor author : authors) {
                if (!authorMap.containsKey(author.getAuthor())) {
                    authorMap.put(author.getAuthor(), author.getAuthor());
                }
                author.setAuthor(authorMap.get(author.getAuthor()));

                if (!roleMap.containsKey(author.getRole())) {
                    roleMap.put(author.getRole(), author.getRole());
                }
                author.setRole(roleMap.get(author.getRole()));
            }
        }
    }

    public List<AuthorStatisticsDTO> getAllAuthors(AuthorSearchCriteria criteria) {
        return authorRepository.getAll(criteria);
    }
}

