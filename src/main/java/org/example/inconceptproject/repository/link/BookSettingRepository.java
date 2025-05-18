package org.example.inconceptproject.repository.link;

import org.example.inconceptproject.model.link.BookCharacter;
import org.example.inconceptproject.model.link.BookSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSettingRepository extends JpaRepository<BookSetting, Long> {
}
