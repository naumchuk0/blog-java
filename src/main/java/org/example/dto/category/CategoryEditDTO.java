package org.example.dto.category;

import lombok.Data;
import org.example.entities.PostEntity;

import java.util.List;

@Data
public class CategoryEditDTO {
    private int id;
    private String name;
    private String description;
}
