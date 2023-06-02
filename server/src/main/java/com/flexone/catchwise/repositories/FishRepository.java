package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.Fish;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface FishRepository extends JpaRepository<Fish, Long> {


    Optional<Fish> findBySpecies(String species);
    List<Fish> findAllBySpeciesIn(List<String> species);
    Fish findByCommonNamesContaining(String commonName);
}
