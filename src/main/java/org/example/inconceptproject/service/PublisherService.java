package org.example.inconceptproject.service;

import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Publisher;
import org.example.inconceptproject.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;


    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<Publisher> getPublisherById(Long id) {
        return publisherRepository.findById(id);
    }

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(Long id, Publisher publisher) {
        Publisher existing = publisherRepository.findById(id).orElseThrow();
        existing.setName(publisher.getName());
        return publisherRepository.save(existing);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }


    public void loadPublishers(Set<BookCsvDTO> books) {
        Map<Publisher, Publisher> publisherMap = publisherRepository.findAll().stream()
                .collect(Collectors.toMap(publisher -> publisher, Function.identity()));

        for (BookCsvDTO book : books) {
            if (book.getPublisher() == null){continue;}

            if(!publisherMap.containsKey(book.getPublisher())) {
                publisherMap.put(book.getPublisher(), book.getPublisher());
            }
            book.setPublisher(publisherMap.get(book.getPublisher()));
        }
    }
}
