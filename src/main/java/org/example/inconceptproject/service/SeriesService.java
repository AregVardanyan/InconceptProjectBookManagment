package org.example.inconceptproject.service;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Language;
import org.example.inconceptproject.model.Series;
import org.example.inconceptproject.repository.SeriesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public void loadSeries(Set<BookCsvDTO> books) {
        Map<Series, Series> seriesMap = seriesRepository.findAll().stream()
                .collect(Collectors.toMap(series -> series, Function.identity()));

        for (BookCsvDTO book : books) {
            if (book.getSeries().getName().isBlank()){
                book.getSeries().setName(book.getTitle());
            }

            if(!seriesMap.containsKey(book.getSeries())) {
                seriesMap.put(book.getSeries(), book.getSeries());
            }
            book.setSeries(seriesMap.get(book.getSeries()));
        }
    }
    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    public Optional<Series> getSeriesById(Long id) {
        return seriesRepository.findById(id);
    }

    public List<String> getAllSeriesCharacters(Long id) {
        return seriesRepository.findAllCharacters(id);
    }

}
