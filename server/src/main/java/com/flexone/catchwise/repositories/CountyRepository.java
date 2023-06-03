package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.County;
import com.flexone.catchwise.domain.Lake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {

    Optional<County> findByName(String county);
    Optional<County> findByLocalId(String localId);


    @Query(value = "FROM Lake l WHERE l.coordinates.latitude BETWEEN ?1 AND ?2 AND l.coordinates.longitude BETWEEN ?3 AND ?4")
    List<County> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);

}
