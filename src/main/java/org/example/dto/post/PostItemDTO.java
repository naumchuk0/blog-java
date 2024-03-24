package org.example.dto.post;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entities.CategoryEntity;
import org.example.entities.PostTagEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostItemDTO {
    private int id;
    private String title;
    private String shortDescription;
    private String description;
    private String meta;
    private String urlSlug;
    private boolean published;
    private LocalDateTime postedOn;
    private LocalDateTime modified;
    private int categoryId;
}
