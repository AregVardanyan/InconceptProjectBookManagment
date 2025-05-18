package org.example.inconceptproject.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.example.inconceptproject.enums.FileDownloadStatus;

@Entity
@Table(name = "file_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_info_id_seq")
    @SequenceGenerator(
            name = "file_info_id_seq",
            sequenceName = "file_info_id_seq",
            allocationSize = 50)
    private Long id;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_format")
    private String fileFormat;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private FileDownloadStatus status;

    @Column(name = "error_message")
    private String errorMessage;
}
