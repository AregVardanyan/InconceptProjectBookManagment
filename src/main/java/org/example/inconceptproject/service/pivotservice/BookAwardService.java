package org.example.inconceptproject.service.pivotservice;


import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.repository.link.BookAwardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookAwardService {

    private final BookAwardRepository bookAwardRepository;


}
