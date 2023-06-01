package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakeBase;

import java.util.List;

public interface LakeService {

    Lake findById(Long id);
    List<LakeBase> findAll();
    List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);
}
