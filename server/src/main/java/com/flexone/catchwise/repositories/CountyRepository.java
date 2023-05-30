package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.County;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {
    Optional<County> findByName(String county);
}
