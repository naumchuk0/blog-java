package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.post.PostCreateDTO;
import org.example.dto.post.PostEditDTO;
import org.example.dto.post.PostItemDTO;
import org.example.entities.CategoryEntity;
import org.example.entities.PostEntity;
import org.example.mapper.PostMapper;
import org.example.repositories.CategoryRepository;
import org.example.repositories.PostRepository;
import org.example.services.UrlSlugGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<PostEntity>> index() {
        List<PostEntity> list = postRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostItemDTO> create(@ModelAttribute PostCreateDTO dto) {
        try {
            PostEntity entity = postMapper.postCreateDTO(dto);
            var category = categoryRepository.findAll();
            if (category == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            entity.setUrlSlug(UrlSlugGenerator.generateUrlSlug(entity.getTitle()));
            entity.setPostedOn(LocalDateTime.now());
            entity.setCategory(category.get(dto.getCategory_id()));
            postRepository.save(entity);
            return new ResponseEntity<>(postMapper.postItemDTO(entity), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostItemDTO> getById(@PathVariable int id) {
        var entity = postRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var result =  postMapper.postItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(path="/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostItemDTO> edit(@ModelAttribute PostEditDTO model) {
        var old = postRepository.findById(model.getId()).orElse(null);
        var category = categoryRepository.findAll();
        if (old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var entity = postMapper.postEditDto(model);
        entity.setUrlSlug(UrlSlugGenerator.generateUrlSlug(entity.getTitle()));
        entity.setModified(LocalDateTime.now());
        entity.setPostedOn(old.getPostedOn());
        entity.setCategory(category.get(model.getCategory_id()));
        postRepository.save(entity);
        var result = postMapper.postItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        var entity = postRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostItemDTO>> searchByName(@RequestParam(required = false) String title,
                                                              Pageable pageable) {
        Page<PostEntity> posts = postRepository.findByTitleContainingIgnoreCase(title, pageable);
        Page<PostItemDTO> result = posts.map(postMapper::postItemDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
