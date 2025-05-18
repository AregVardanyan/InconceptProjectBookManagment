package org.example.inconceptproject.service.csvrelated;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.model.Book;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.RatingByStars;
import org.example.inconceptproject.model.link.BookCharacter;
import org.example.inconceptproject.model.link.BookSetting;
import org.example.inconceptproject.repository.BookRepository;
import org.example.inconceptproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CSVToDtoService {
    private final BookRepository bookRepository;

    private final LanguageService languageService;
    private final PublisherService publisherService;
    private final EditionService editionService;
    private final FormatService formatService;
    private final SeriesService seriesService;

    private static final int THREAD_POOL_SIZE = 10;
    private final AuthorService authorService;
    private final AwardService awardService;
    private final GenreService genreService;
    private final RatingsByStarsService ratingsByStarsService;
    private final SettingService settingService;
    private final CharacterService characterService;
    private final BookService bookService;
    private final CoverImageService coverImageService;

    public Queue<Book> uploadBooksCSV(MultipartFile file) throws Exception {
        List<BookCsvDTO> parsedBooks = parseCsv(file);
        return uploadBooks(parsedBooks);
    }

    private List<BookCsvDTO> parseCsv(MultipartFile file) throws Exception {
        try (InputStream input = file.getInputStream()) {
            CsvMapper mapper = new CsvMapper();
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);

            CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');

            MappingIterator<BookCsvDTO> it = mapper.readerFor(BookCsvDTO.class)
                    .with(schema)
                    .readValues(input);

            return it.readAll();
        }
    }

    public Queue<Book> uploadBooks(List<BookCsvDTO> books) throws Exception {
        Set<BookCsvDTO> processedBooks = processBooksInParallel(books);

        languageService.loadLanguages(processedBooks);
        publisherService.loadPublishers(processedBooks);
        editionService.loadEditions(processedBooks);
        formatService.loadFormats(processedBooks);
        seriesService.loadSeries(processedBooks);
        authorService.loadAuthors(processedBooks);
        awardService.loadAwards(processedBooks);
        genreService.loadGenres(processedBooks);
        settingService.loadSettings(processedBooks);
        characterService.loadCharacters(processedBooks);


        Queue<Book> newBooks = bookService.loadBooks(processedBooks);
        bookRepository.saveAll(newBooks);

        coverImageService.processBookCover(newBooks.stream().toList());

        return newBooks;
    }


    private Set<BookCsvDTO> processBooksInParallel(List<BookCsvDTO> books) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<BookCsvDTO>> futures = new ArrayList<>();
        for (BookCsvDTO book : books) {
            futures.add(executor.submit(() -> process(book)));
        }

        Set<BookCsvDTO> results = new HashSet<>();
        for (Future<BookCsvDTO> future : futures) {
            try {
                BookCsvDTO processed = future.get();
                if (processed != null) results.add(processed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return results;
    }

    private BookCsvDTO process(BookCsvDTO book) {

        return book;
    }
}
