package com.allicaihub.inventory.web.controller;

import com.allicaihub.inventory.persistence.entity.Category;
import com.allicaihub.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<Category>> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map( category -> ResponseEntity.ok().body(category) )
                .defaultIfEmpty( ResponseEntity.notFound().build() );
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Category>> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category)
                .map( savedCategory -> {
                    URI newEntityLocation = URI.create("/api/v1/category/get/" + savedCategory.getCategoryId() );
                    return ResponseEntity.created(newEntityLocation).body(savedCategory);
                })
                .defaultIfEmpty( ResponseEntity.unprocessableEntity().body(category) );
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Category>> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .flatMap( categoryToUpdate -> {
                    category.setCategoryId(categoryToUpdate.getCategoryId());
                    return categoryService.addCategory(category);
                }).map( categoryUpdated -> ResponseEntity.accepted().body(categoryUpdated) )
                .defaultIfEmpty( ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategoryById(id);
    }
}
