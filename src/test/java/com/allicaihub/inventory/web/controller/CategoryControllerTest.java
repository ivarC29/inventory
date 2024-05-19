package com.allicaihub.inventory.web.controller;

import com.allicaihub.inventory.persistence.entity.Category;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private Category savedCategory;

    @BeforeAll
    void setUp() {
        System.out.println("hola");
    }

    @DisplayName("")
    @Test
    @Order(0)
    void testSaveCategory() {
        Flux<Category> categoryFlux = webTestClient.post()
                .uri("/api/v1/category/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Category.builder()
                                .categoryName("CategoryTest")
                                .categoryDescription("Description of CategoryTest")
                        .build()))
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Category.class).getResponseBody()
                .log();

        categoryFlux.next().subscribe(category -> {
            this.savedCategory = category;
        });

        Assertions.assertNotNull(savedCategory);
    }

    @DisplayName("")
    @Test
    @Order(1)
    void getAllCategories() {
        Flux<Category> categoryFlux = webTestClient.get()
                .uri("/api/v1/category")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Category.class).getResponseBody()
                .log();

        StepVerifier.create(categoryFlux)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @DisplayName("")
    @Test
    @Order(2)
    void getCategoryById() {
        Flux<Category> categoryFlux = webTestClient.get()
                .uri("/api/v1/category/get/{id}",savedCategory.getCategoryId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Category.class).getResponseBody()
                .log();

        StepVerifier.create(categoryFlux)
                .expectSubscription()
                .expectNextMatches(client -> client.getCategoryName().equals("CategoryTest"))
                .verifyComplete();
    }

    @DisplayName("")
    @Test
    @Order(3)
    void updateCategory() {
        Flux<Category> categoryFlux = webTestClient.put()
                .uri("/api/v1/category/update/{id}",savedCategory.getCategoryId())
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        Category.builder()
                            .categoryName("Updated category test")
                            .categoryDescription("Description of category updated tes.")
                            .build()
                ))
                .exchange()
                .returnResult(Category.class).getResponseBody()
                .log();

        StepVerifier.create(categoryFlux)
                .expectSubscription()
                .expectNextMatches(category -> category.getCategoryName().equals("Updated category test"))
                .verifyComplete();
    }

    @DisplayName("")
    @Test
    @Order(4)
    void deleteCategory() {
        Flux<Void> flux = webTestClient.delete()
                .uri("/api/v1/category/delete/{id}", savedCategory.getCategoryId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Void.class).getResponseBody();

        StepVerifier.create(flux)
                .expectSubscription()
                .verifyComplete();
    }
}