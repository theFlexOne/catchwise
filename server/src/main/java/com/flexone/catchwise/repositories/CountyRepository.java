package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.County;
<<<<<<< HEAD
import com.flexone.catchwise.domain.Lake;
=======
import com.flexone.catchwise.domain.State;
>>>>>>> 704d6b66c9d39fa127599ed7bea093d15f86b9d3
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountyRepository extends JpaRepository<County, Long> {

    Optional<County> findByName(String county);
<<<<<<< HEAD
    Optional<County> findByLocalId(String localId);


    @Query(value = "FROM Lake l WHERE l.coordinates.latitude BETWEEN ?1 AND ?2 AND l.coordinates.longitude BETWEEN ?3 AND ?4")
    List<County> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);
=======

    @Query("FROM County c WHERE c.geoData.geoId = ?1")
    Optional<County> findByGeoDataGeoId(String fipsId);
    List<County> findAllByStateId(Long stateId);
    List<County> findAllByState(State state);


>>>>>>> 704d6b66c9d39fa127599ed7bea093d15f86b9d3

}
