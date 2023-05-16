package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Fish;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FishService {

    Fish save(Fish fish);
    List<Fish> saveAll(List<Fish> fishList);

    Fish findById(Long id);
    Fish findByName(String name);
    Fish findBySpecies(String species);
    List<Fish> findAll();

    void deleteById(Long id);
}
