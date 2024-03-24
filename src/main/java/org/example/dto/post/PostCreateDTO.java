package org.example.dto.post;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCreateDTO {
    private String title;
    private String shortDescription;
    private String description;
    private String meta;
    private boolean published;
    private int category_id;
}
