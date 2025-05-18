package org.example.inconceptproject.service;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Award;
import org.example.inconceptproject.model.link.BookAward;
import org.example.inconceptproject.repository.AwardRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;

    public List<Award> getAllAwards() {
        return awardRepository.findAll();
    }

    public Optional<Award> getAwardById(Long id) {
        return awardRepository.findById(id);
    }

    public void loadAwards(Set<BookCsvDTO> books) {
        Map<Award, Award> awardMap = awardRepository.findAll().stream()
                .collect(Collectors.toMap(award -> award, Function.identity()));

        for (BookCsvDTO book : books) {
            List<BookAward> awards = book.getAwards();

            for (BookAward award : awards) {
                if (award.getAward() == null || award.getAward().getName().isBlank()) continue;

                if (!awardMap.containsKey(award.getAward())) {
                    awardMap.put(award.getAward(), award.getAward());
                }
                award.setAward(awardMap.get(award.getAward()));
            }
        }
    }
}