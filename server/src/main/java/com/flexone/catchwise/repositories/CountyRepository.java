package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.County;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountyRepository extends JpaRepository<County, Long> {
    County findByName(String county);
}
