package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.repositories.FishRepository;
import com.flexone.catchwise.services.FishService;

import java.util.List;

public class FishServiceImpl implements FishService {

    private final FishRepository fishRepository;

    public FishServiceImpl(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    @Override
    public Fish save(Fish fish) {
        return null;
    }

    @Override
    public List<Fish> saveAll(List<Fish> fishList) {
        return (List<Fish>) fishRepository.saveAll(fishList);
    }

    @Override
    public Fish findById(Long id) {
        return null;
    }

    @Override
    public Fish findByName(String name) {
        return null;
    }

    @Override
    public Fish findBySpecies(String species) {
        return null;
    }

    @Override
    public List<Fish> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
