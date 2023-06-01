package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.Lake;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LakeRepository extends JpaRepository<Lake, Long> {

//    @Override
//    @NotNull
//    @Query("SELECT l FROM Lake l WHERE l.fish.size > 0 AND l.coordinates IS NOT NULL AND l.name IS NOT NULL")
//    List<Lake> findAll();
//
//    @Override
//    @NotNull
//    @Query("SELECT l FROM Lake l WHERE l.fish.size > 0 AND l.coordinates IS NOT NULL AND l.name IS NOT NULL")
//    Page<Lake> findAll(Pageable pageable);
//
//    @Query("SELECT l FROM Lake l WHERE l.fish.size > 0 AND l.coordinates IS NOT NULL AND l.name IS NOT NULL AND l.coordinates.latitude BETWEEN :minLat AND :maxLat AND l.coordinates.longitude BETWEEN :minLng AND :maxLng")
//    List<Lake> findAllInRange(@NonNull Double minLat, @NonNull Double maxLat, @NonNull Double minLng, @NonNull Double maxLng);
//
//    @Query("SELECT l FROM Lake l WHERE l.fish.size > 0 AND l.coordinates IS NOT NULL AND l.name IS NOT NULL AND l.coordinates.latitude BETWEEN :minLat AND :maxLat AND l.coordinates.longitude BETWEEN :minLng AND :maxLng")
//    Page<Lake> findAllInRange(@NonNull Double minLat, @NonNull Double maxLat, @NonNull Double minLng, @NonNull Double maxLng, Pageable pageable);
//
//    @Query("SELECT l FROM Lake l WHERE l.coordinates IS NOT NULL AND l.name IS NOT NULL AND l.localId LIKE '%0'")
//    List<Lake> findAllLakes();
//
//    @Query("SELECT l FROM Lake l WHERE l.fish.size > 0 AND l.coordinates IS NOT NULL AND l.name IS NOT NULL AND l.localId = :localId")
//    Lake findByLocalId(@NonNull String localId);

}
