package org.example.repositories;

import org.example.entities.CategoryEntity;
import org.example.entities.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    Page<PostEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
