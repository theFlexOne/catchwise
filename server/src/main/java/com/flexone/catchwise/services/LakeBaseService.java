package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakeBase;

import java.util.List;

public interface LakeBaseService {

    LakeBase findById(Long id);
    List<LakeBase> findAll();
    List<LakeBase> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);

    LakeBase findByLocalId(String parentLocalId);
}
