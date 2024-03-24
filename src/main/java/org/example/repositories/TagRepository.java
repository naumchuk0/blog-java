package org.example.repositories;

import org.example.entities.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    Page<TagEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
