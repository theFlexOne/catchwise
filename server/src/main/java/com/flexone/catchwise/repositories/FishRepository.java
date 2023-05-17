package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.Fish;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface FishRepository extends CrudRepository<Fish, Long> {

    Fish findBySpecies(String species);
    List<Fish> findAllBySpeciesIn(List<String> species);
}
