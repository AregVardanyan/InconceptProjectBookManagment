package org.example.inconceptproject.service;

import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Format;
import org.example.inconceptproject.repository.FormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FormatService {
    @Autowired
    FormatRepository formatRepository;


    public List<Format> getAllFormats() {
        return formatRepository.findAll();
    }

    public Optional<Format> getFormatById(Long id) {
        return formatRepository.findById(id);
    }

    public Format saveFormat(Format format) {
        return formatRepository.save(format);
    }

    public Format updateFormat(Long id, Format updated) {
        return formatRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    return formatRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Format not found"));
    }

    public void deleteFormat(Long id) {
        formatRepository.nullifyFormatInBooks(id);
        formatRepository.deleteById(id);
    }


    public void loadFormats(Set<BookCsvDTO> books) {
        Map<Format, Format> formatMap = formatRepository.findAll().stream()
                .collect(Collectors.toMap(format -> format, Function.identity()));

        for (BookCsvDTO book : books) {
            if (book.getBookFormat() == null){continue;}

            if(!formatMap.containsKey(book.getBookFormat())) {
                formatMap.put(book.getBookFormat(), book.getBookFormat());
            }
            book.setBookFormat(formatMap.get(book.getBookFormat()));
        }

    }

}
