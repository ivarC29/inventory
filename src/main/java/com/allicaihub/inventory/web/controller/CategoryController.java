package com.allicaihub.inventory.web.controller;

import com.allicaihub.inventory.persistence.entity.Category;
import com.allicaihub.inventory.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories", description = "Returns a stream of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Category> getAllCategories() {
        return categoryService.getAllCategories()
                .onErrorResume(e -> {
                    log.error("Error getting all categories", e);
                    return Flux.error(new RuntimeException("Error retrieving all categories"));
                });
    }

    @Operation(summary = "Get category by id", description = "Returns a category entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve category"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<Category>> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map( category -> ResponseEntity.ok().body(category) )
                .defaultIfEmpty( ResponseEntity.notFound().build() )
                .onErrorResume( e -> {
                    log.error("Error retrieving category", e);
                    return Mono.error(new RuntimeException("Error retrieving category"));
                });
    }

    @Operation(summary = "Add new category entity", description = "Returns a created category entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully - Created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity - client error."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/add")
    public Mono<ResponseEntity<Category>> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category)
                .map( savedCategory -> {
                    URI newEntityLocation = URI.create("/api/v1/category/get/" + savedCategory.getCategoryId() );
                    return ResponseEntity.created(newEntityLocation).body(savedCategory);
                })
                .defaultIfEmpty( ResponseEntity.unprocessableEntity().body(category) )
                .onErrorResume(e -> {
                    log.error("Error adding category", e);
                    return Mono.error(new RuntimeException("Error adding category"));
                });
    }

    @Operation(summary = "Update category", description = "Update fields of category entity")
    @ApiResponses(value={
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Category>> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .flatMap( categoryToUpdate -> {
                    category.setCategoryId(categoryToUpdate.getCategoryId());
                    return categoryService.addCategory(category);
                }).map( categoryUpdated -> ResponseEntity.accepted().body(categoryUpdated) )
                .defaultIfEmpty( ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    log.error("Error updating category", e);
                    return Mono.error(new RuntimeException("Error updating category"));
                });
    }

    @Operation(summary = "Delete category", description = "Delete a category entity by id")
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteCategoryById(@PathVariable Long id) {
        return categoryService.deleteCategoryById(id);
    }
}
