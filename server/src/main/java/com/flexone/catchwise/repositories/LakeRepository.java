package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.Lake;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LakeRepository extends JpaRepository<Lake, Long> {


    @Override
    @NonNull
    List<Lake> findAll();

    Page<Lake> findAllByCounty(String county, Pageable pageable);

    @Query("SELECT l FROM Lake l WHERE l.coordinates.latitude >= :minLat AND l.coordinates.latitude <= :maxLat AND l.coordinates.longitude >= :minLng AND l.coordinates.longitude <= :maxLng AND SUBSTRING(l.localId, LENGTH(l.localId) - 1, 1) = '0'")
    List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);

    @Query("SELECT l FROM Lake l JOIN FETCH l.fish WHERE SIZE(l.fish) > 0 AND SUBSTRING(l.localId, LENGTH(l.localId) - 1, 1) = '0'")
    List<Lake> findAllWithFish();
}