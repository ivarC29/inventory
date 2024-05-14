package com.allicaihub.inventory.web.controller;

import com.allicaihub.inventory.persistence.entity.Category;
import com.allicaihub.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/add")
    Mono<Category> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

}
