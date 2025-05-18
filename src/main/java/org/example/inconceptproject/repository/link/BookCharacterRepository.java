package org.example.inconceptproject.repository.link;

import org.example.inconceptproject.model.link.BookCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCharacterRepository extends JpaRepository<BookCharacter, Long> {
}
