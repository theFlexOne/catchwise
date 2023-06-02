package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.Lake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeRepository extends JpaRepository<Lake, Long> {


    Lake findByLocalId(String localId);

    @Query(value = "FROM Lake l WHERE l.coordinates.latitude BETWEEN ?1 AND ?2 AND l.coordinates.longitude BETWEEN ?3 AND ?4")
    List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);
}
