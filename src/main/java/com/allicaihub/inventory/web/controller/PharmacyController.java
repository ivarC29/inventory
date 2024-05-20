package com.allicaihub.inventory.web.controller;

import com.allicaihub.inventory.persistence.entity.Pharmacy;
import com.allicaihub.inventory.service.PharmacyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/pharmacy")
public class PharmacyController {

    private static final Logger log = LoggerFactory.getLogger(PharmacyController.class);

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @Operation(summary = "Get all pharmacies", description = "Returns a stream of all pharmacies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value="/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Pharmacy> getAllPharmacies() {
        return pharmacyService.getAllPharmacies()
                .onErrorResume(e -> {
                    log.error("Error retrieving pharmacies", e);
                    return Flux.error( new RuntimeException("Error retrieving pharmacies") );
                });
    }



}
