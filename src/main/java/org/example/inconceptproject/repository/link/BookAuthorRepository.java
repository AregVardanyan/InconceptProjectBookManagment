package org.example.inconceptproject.repository.link;

import org.example.inconceptproject.model.link.BookAuthor;
import org.example.inconceptproject.model.link.BookAward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
}
