package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.County;
import com.flexone.catchwise.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {

    Optional<County> findByName(String county);

    @Query("FROM County c WHERE c.geoData.geoId = ?1")
    Optional<County> findByGeoDataGeoId(String fipsId);
    List<County> findAllByStateId(Long stateId);
    List<County> findAllByState(State state);



}
