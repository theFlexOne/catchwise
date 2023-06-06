package com.flexone.catchwise.repository;

import com.flexone.catchwise.domain.Fish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface FishRepository extends JpaRepository<Fish, Long> {


    Optional<Fish> findBySpecies(String species);
    List<Fish> findAllBySpeciesIn(List<String> species);
    Fish findByCommonNamesContaining(String commonName);
}
