package org.example.inconceptproject.repository;

import jakarta.transaction.Transactional;
import org.example.inconceptproject.model.Characterr;
import org.example.inconceptproject.model.Language;
import org.example.inconceptproject.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByName(String name);



//    boolean existsByName(String settingName);
//
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM book_setting WHERE setting_id = :settingId", nativeQuery = true)
//    void deleteBookSettingMappingsBySettingId(@Param("settingId") Long settingId);
}
