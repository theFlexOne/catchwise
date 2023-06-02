package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.repositories.FishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FishService {

    private final FishRepository fishRepository;

    public Fish findById(Long id) {
        return fishRepository.findById(id).orElse(null);
    }

    public Page<Fish> findAll(int page, int size, Sort.Direction direction, String sortProperty) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));
        return fishRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        fishRepository.deleteById(id);
    }

    public Fish findBySpecies(String species) {
        return fishRepository.findBySpecies(species).orElseThrow();
    }
}
