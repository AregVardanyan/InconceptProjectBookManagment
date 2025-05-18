package org.example.inconceptproject.service;

import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.*;
import org.example.inconceptproject.repository.BookRepository;
import org.example.inconceptproject.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RatingsByStarsService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    public void loadRatingByStars(Set<BookCsvDTO> books) {
        Map<RatingByStars, RatingByStars> ratingByStarsMap = ratingRepository.findAll().stream()
                .collect(Collectors.toMap(ratingByStars -> ratingByStars, Function.identity()));

        for (BookCsvDTO book : books) {
            List<RatingByStars> ratingByStars = book.getRatingsByStars();
            int sum = 0;
            int weightSum = 0;
            for (RatingByStars rating : ratingByStars) {
                weightSum += rating.getCount() * rating.getStars();
                sum += rating.getCount();
            }

        }
    }

    public void addStar(Long bookId, Integer star) {
        Optional<Book> book  = bookRepository.findById(bookId);
        if(!book.isPresent()){
            return;
        }
        if(star != null && star > 0 && star<=5){
            book.get().getRatingByStars().get(star).setCount(book.get().getRatingByStars().get(star).getCount() + 1);
        }
    }
}