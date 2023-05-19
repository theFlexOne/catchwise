package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.Lake;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LakeRepository extends JpaRepository<Lake, Long> {


    @Override
    @NonNull
    Page<Lake> findAll(@NonNull Pageable pageable);

    Page<Lake> findAllByCounty(String county, Pageable pageable);
}
