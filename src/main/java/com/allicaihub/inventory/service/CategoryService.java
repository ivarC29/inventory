package com.allicaihub.inventory.service;

import com.allicaihub.inventory.persistence.entity.Category;
import com.allicaihub.inventory.persistence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Mono<Category> addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Mono<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Mono<Void> deleteCategoryById(Long id) {
        return categoryRepository.deleteById(id);
    }

}
