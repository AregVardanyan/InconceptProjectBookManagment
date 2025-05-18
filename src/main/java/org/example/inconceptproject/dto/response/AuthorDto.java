package org.example.inconceptproject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorDto {
    private Long authorId;
    private String aurhorName;
    private Long roleId;
    private String roleName;
}
