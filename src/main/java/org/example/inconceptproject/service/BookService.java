package org.example.inconceptproject.service;

import lombok.RequiredArgsConstructor;
import org.example.inconceptproject.criteria.BookSearchCriteria;
import org.example.inconceptproject.dto.request.BookCsvDTO;
import org.example.inconceptproject.dto.request.BookCreateDto;
import org.example.inconceptproject.dto.request.BookUpdateDto;
import org.example.inconceptproject.dto.response.BookInfoDTO;
import org.example.inconceptproject.dto.response.BookLessInfoDTO;
import org.example.inconceptproject.dto.response.PageResponseDto;
import org.example.inconceptproject.model.*;
import org.example.inconceptproject.model.link.*;
import org.example.inconceptproject.repository.*;
import org.example.inconceptproject.repository.link.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final LanguageRepository languageRepository;
    private final EditionRepository editionRepository;
    private final PublisherRepository publisherRepository;
    private final SeriesRepository seriesRepository;
    private final FormatRepository formatRepository;
    private final GenreRepository genreRepository;
    private final CharacterRepository characterRepository;
    private final AwardRepository awardRepository;
    private final RatingRepository ratingRepository;
    private final AuthorRoleRepository authorRoleRepository;
    private final AuthorRepository authorRepository;
    private final SettingRepository settingRepository;
    private final BookGenreRepository bookGenreRepository;
    private final BookAwardRepository bookAwardRepository;
    private final BookSettingRepository bookSettingRepository;
    private final BookCharacterRepository bookCharacterRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final CoverImageService coverImageService;


    @Transactional
    public Queue<Book> loadBooks(Set<BookCsvDTO> mappedBooks) {

        Map<Long, Book> bookMap = bookRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Book::getNaturalBookId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        Set<String> isbnSet = bookRepository.findAll()
                .stream()
                .map(Book::getIsbn)
                .collect(Collectors.toSet());
        Queue<Book> newBooks = new ConcurrentLinkedQueue<>();

        mappedBooks.parallelStream()
                .filter(mappedBook -> !bookMap.containsKey(mappedBook.getBookId()))
                .forEach(mappedBook -> {
                    Book book = mappedBook.mapToBook();
                    if(isbnSet.contains(book.getIsbn())){
                        book.setIsbn(null);
                    }else {isbnSet.add(book.getIsbn());}
                    newBooks.add(book);
                });

        return newBooks;
    }

    public PageResponseDto<BookLessInfoDTO> getAll(BookSearchCriteria criteria) {
        Page<Book> page = bookRepository.findAll(
                criteria,
                criteria.buildPageRequest()
        );
        Page<BookLessInfoDTO> bookDTOPage = page.map(BookLessInfoDTO::toDto);
        return PageResponseDto.from(bookDTOPage);
    }

    public BookInfoDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
        return BookInfoDTO.toDto(book);
    }

    public BookInfoDTO uploadBook(BookCreateDto bookDto) {
        Book book = bookDto.toBook();

        if(bookRepository.existsBookById(book.getId()) || bookRepository.existsBookByTitle(book.getTitle())){
            return null;
        }
        languageRepository.findByName(book.getLanguage().getName()).ifPresent(book::setLanguage);
        seriesRepository.findByName(book.getSeries().getName()).ifPresent(book::setSeries);
        editionRepository.findByName(book.getEdition().getName()).ifPresent(book::setEdition);
        publisherRepository.findByName(book.getPublisher().getName()).ifPresent(book::setPublisher);
        formatRepository.findByName(book.getBookFormat().getName()).ifPresent(book::setBookFormat);

        for(BookGenre bookGenre : book.getGenres()){
            genreRepository.findByName(bookGenre.getGenre().getName()).ifPresent(bookGenre::setGenre);
        }

        for (BookAward bookAward : book.getAwards()) {
            awardRepository.findByName(bookAward.getAward().getName()).ifPresent(bookAward::setAward);
        }

        for (BookSetting bookSetting: book.getSettings()){
            settingRepository.findByName(bookSetting.getSetting().getName()).ifPresent(bookSetting::setSetting);
        }

        for (BookCharacter bookCharacter : book.getCharacters()) {
            characterRepository.findByName(bookCharacter.getCharacter().getName()).ifPresent(bookCharacter::setCharacter);
        }

        for (BookAuthor bookAuthor : book.getAuthors()) {
            authorRepository.findByName(bookAuthor.getAuthor().getName()).ifPresent(bookAuthor::setAuthor);
        }

        bookRepository.save(book);
        coverImageService.download(book.getCoverImg());
        return BookInfoDTO.toDto(book);
    }

    public BookInfoDTO updateBook(BookUpdateDto updateDto, Long id) {

        Book book = bookRepository.findById(id).orElseThrow();

        if (updateDto.getTitle() != null) {
            book.setTitle(updateDto.getTitle());
        }

        if (updateDto.getIsbn() != null) {
            book.setIsbn(updateDto.getIsbn());
        }
        
        if (updateDto.getPrice() != null) {
            book.setPrice(updateDto.getPrice());
        }
        
        if(updateDto.getPages() != null){
            book.setPages(updateDto.getPages());
        }
        
        if(updateDto.getDescription() != null){
            book.setDescription(updateDto.getDescription());
        }
        
        if(updateDto.getFirstPublishDate() != null){
            book.setFirstPublishDate(updateDto.getFirstPublishDate());
        }

        if (updateDto.getCoverImg() != null) {
            book.setCoverImg(updateDto.getCoverImg());
            coverImageService.download(updateDto.getCoverImg());
        }

        if (updateDto.getLanguage() != null && updateDto.getLanguage().getName() != null) {
            book.setLanguage(updateDto.getLanguage());
            languageRepository.findByName(updateDto.getLanguage().getName()).ifPresent(book::setLanguage);
        }

        if (updateDto.getSeries() != null && updateDto.getSeries().getName() != null) {
            book.setSeries(updateDto.getSeries());
            seriesRepository.findByName(updateDto.getSeries().getName()).ifPresent(book::setSeries);
        }

        if (updateDto.getEdition() != null && updateDto.getEdition().getName() != null) {
            book.setEdition(updateDto.getEdition());
            editionRepository.findByName(updateDto.getEdition().getName()).ifPresent(book::setEdition);
        }

        if (updateDto.getPublisher() != null && updateDto.getPublisher().getName() != null) {
            book.setPublisher(updateDto.getPublisher());
            publisherRepository.findByName(updateDto.getPublisher().getName()).ifPresent(book::setPublisher);
        }

        if (updateDto.getFormat() != null && updateDto.getFormat().getName() != null) {
            book.setBookFormat(updateDto.getFormat());
            formatRepository.findByName(updateDto.getFormat().getName()).ifPresent(book::setBookFormat);
        }

        addGenresToBook(book, updateDto);
        removeGenresFromBook(book, updateDto);
        addAwards(book, updateDto);
        removeAwards(book, updateDto);
        addSettings(book, updateDto);
        removeSettings(book, updateDto);
        addCharacters(book, updateDto);
        removeCharacters(book, updateDto);
        addAuthors(book, updateDto);
        removeAuthors(book, updateDto);


        bookRepository.save(book);
        return BookInfoDTO.toDto(book);
    }


    private void addGenresToBook(Book book, BookUpdateDto updateDto) {
        if (updateDto.getAddGenres() != null) {
            for (BookGenre bookGenre : updateDto.getAddGenres()) {
                genreRepository.findByName(bookGenre.getGenre().getName())
                        .ifPresent(bookGenre::setGenre);
                book.addGenre(bookGenre);
            }
        }
    }

    private void removeGenresFromBook(Book book, BookUpdateDto updateDto) {
        if (updateDto.getRemoveGenres() != null) {
            List<BookGenre> toRemove = book.getGenres().stream()
                    .filter(bg -> updateDto.getRemoveGenres().contains(bg.getGenre().getId()))
                    .collect(Collectors.toList());
            book.removeGenres(toRemove);
            bookGenreRepository.deleteAll(toRemove);
        }
    }

    private void addAwards(Book book, BookUpdateDto updateDto) {
        if (updateDto.getAddAwards() != null) {
            for (BookAward bookAward : updateDto.getAddAwards()) {
                awardRepository.findByName(bookAward.getAward().getName()).ifPresent(bookAward::setAward);
                book.addAward(bookAward);
            }
        }
    }

    private void removeAwards(Book book, BookUpdateDto updateDto) {
        if (updateDto.getRemoveAwards() != null) {
            List<BookAward> removeList = book.getAwards().stream()
                    .filter(ba -> updateDto.getRemoveAwards().contains(ba.getAward().getId()))
                    .collect(Collectors.toList());
            book.removeAwards(removeList);
            bookAwardRepository.deleteAll(removeList);
        }
    }

    private void addSettings(Book book, BookUpdateDto updateDto) {
        if (updateDto.getAddSettings() != null) {
            for (BookSetting bookSetting : updateDto.getAddSettings()) {
                settingRepository.findByName(bookSetting.getSetting().getName()).ifPresent(bookSetting::setSetting);
                book.addSetting(bookSetting);
            }
        }
    }

    private void removeSettings(Book book, BookUpdateDto updateDto) {
        if (updateDto.getRemoveSettings() != null) {
            List<BookSetting> removeList = book.getSettings().stream()
                    .filter(bs -> updateDto.getRemoveSettings().contains(bs.getSetting().getId()))
                    .collect(Collectors.toList());
            book.removeSettings(removeList);
            bookSettingRepository.deleteAll(removeList);
        }
    }

    private void addCharacters(Book book, BookUpdateDto updateDto) {
        if (updateDto.getAddCharacters() != null) {
            for (BookCharacter bookCharacter : updateDto.getAddCharacters()) {
                characterRepository.findByName(bookCharacter.getCharacter().getName()).ifPresent(bookCharacter::setCharacter);
                book.addCharacter(bookCharacter);
            }
        }
    }

    private void removeCharacters(Book book, BookUpdateDto updateDto) {
        if (updateDto.getRemoveCharacters() != null) {
            List<BookCharacter> removeList = book.getCharacters().stream()
                    .filter(bc -> updateDto.getRemoveCharacters().contains(bc.getCharacter().getId()))
                    .collect(Collectors.toList());
            book.removeCharacters(removeList);
            bookCharacterRepository.deleteAll(removeList);
        }
    }

    private void addAuthors(Book book, BookUpdateDto updateDto) {
        if (updateDto.getAddAuthors() != null) {
            for (BookAuthor bookAuthor : updateDto.getAddAuthors()) {
                authorRepository.findByName(bookAuthor.getAuthor().getName()).ifPresent(bookAuthor::setAuthor);
                authorRoleRepository.findByName(bookAuthor.getRole().getName()).ifPresent(bookAuthor::setRole);
                book.addAuthor(bookAuthor);
            }
        }
    }

    private void removeAuthors(Book book, BookUpdateDto updateDto) {
        if (updateDto.getRemoveAuthors() != null) {
            List<BookAuthor> removeList = book.getAuthors().stream()
                    .filter(ba -> updateDto.getRemoveAuthors().contains(ba.getAuthor().getId()))
                    .collect(Collectors.toList());
            book.removeAuthors(removeList);
            bookAuthorRepository.deleteAll(removeList);
        }
    }

    public BookInfoDTO updateBbe(Long id, Integer bbeScore, Integer bbeVotes) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setBbeScore(bbeScore);
        book.setBbeVotes(bbeVotes);
        return BookInfoDTO.toDto(book);
    }
}
