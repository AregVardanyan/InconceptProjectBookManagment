package org.example.inconceptproject.repository.link;

import org.example.inconceptproject.model.link.BookGenre;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {

}
