package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.repositories.FishRepository;
import com.flexone.catchwise.services.FishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FishServiceImpl implements FishService {

    private final FishRepository fishRepository;

    public FishServiceImpl(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    @Override
    public Fish findById(Long id) {
        return fishRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Fish> findAll(int page, int size, Sort.Direction direction, String sortProperty) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));
        return fishRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Fish findBySpecies(String species) {
        return fishRepository.findBySpecies(species);
    }
}
