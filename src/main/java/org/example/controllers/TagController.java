package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.tag.TagCreateDTO;
import org.example.dto.tag.TagEditDTO;
import org.example.dto.tag.TagItemDTO;
import org.example.entities.TagEntity;
import org.example.mapper.TagMapper;
import org.example.repositories.TagRepository;
import org.example.services.UrlSlugGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<TagEntity>> index() {
        List<TagEntity> list = tagRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TagItemDTO> create(@ModelAttribute TagCreateDTO dto) {
        try {
            TagEntity entity = tagMapper.tagCreateDTO(dto);
            entity.setUrlSlug(UrlSlugGenerator.generateUrlSlug(entity.getName()));
            tagRepository.save(entity);
            return new ResponseEntity<>(tagMapper.tagItemDTO(entity), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagItemDTO> getById(@PathVariable int id) {
        var entity = tagRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var result =  tagMapper.tagItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(path="/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TagItemDTO> edit(@ModelAttribute TagEditDTO model) {
        var old = tagRepository.findById(model.getId()).orElse(null);
        if (old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var entity = tagMapper.tagEditDto(model);
        entity.setUrlSlug(UrlSlugGenerator.generateUrlSlug(entity.getName()));
        tagRepository.save(entity);
        var result = tagMapper.tagItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        var entity = tagRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            tagRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TagItemDTO>> searchByName(@RequestParam(required = false) String name,
                                                              Pageable pageable) {
        Page<TagEntity> tags = tagRepository.findByNameContainingIgnoreCase(name, pageable);
        Page<TagItemDTO> result = tags.map(tagMapper::tagItemDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
