package org.example.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostEditDTO {
    private int id;
    private String title;
    private String shortDescription;
    private String description;
    private String meta;
    private boolean published;
    private int category_id;
}
