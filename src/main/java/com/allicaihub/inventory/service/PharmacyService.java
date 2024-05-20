package com.allicaihub.inventory.service;

import com.allicaihub.inventory.persistence.entity.Pharmacy;
import com.allicaihub.inventory.persistence.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyService(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    public Flux<Pharmacy> getAllPharmacies(){
        return pharmacyRepository.findAll();
    }

    public Mono<Pharmacy> getPharmacyById(Long id){
        return pharmacyRepository.findById(id);
    }

    public Mono<Pharmacy> addPharmacy(Pharmacy pharmacy){
        return pharmacyRepository.save(pharmacy);
    }

    public Mono<Pharmacy> updatePharmacy(Pharmacy pharmacy){
        return pharmacyRepository.save(pharmacy);
    }

    public Mono<Void> deletePharmacy(Long id){
        return pharmacyRepository.deleteById(id);
    }
}
