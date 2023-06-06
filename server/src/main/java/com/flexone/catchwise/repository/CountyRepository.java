package com.flexone.catchwise.repository;

import com.flexone.catchwise.domain.County;
import com.flexone.catchwise.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {

    @Query(value = "FROM Lake l WHERE l.coordinates.latitude BETWEEN ?1 AND ?2 AND l.coordinates.longitude BETWEEN ?3 AND ?4")
    List<County> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);

    List<County> findAllByStateId(Long stateId);
    List<County> findAllByState(State state);



}
