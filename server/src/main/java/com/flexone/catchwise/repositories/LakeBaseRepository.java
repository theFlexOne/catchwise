package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakeBase;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeBaseRepository extends JpaRepository<LakeBase, Long> {

    @Override
    @NonNull
    List<LakeBase> findAll();

    Page<LakeBase> findAllByCounty(String county, Pageable pageable);

    @Query("SELECT l FROM Lake l WHERE l.coordinates.latitude >= :minLat AND l.coordinates.latitude <= :maxLat AND l.coordinates.longitude >= :minLng AND l.coordinates.longitude <= :maxLng AND SUBSTRING(l.localId, LENGTH(l.localId) - 1, 1) = '0'")
    List<LakeBase> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);


    LakeBase findByLocalId(String parentLakeId);
}