package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Fish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface FishService {

    Fish findById(Long id);
    Page<Fish> findAll(int page, int size, Sort.Direction direction, String sortProperty);
    void deleteById(Long id);

    Fish findBySpecies(String species);
}
