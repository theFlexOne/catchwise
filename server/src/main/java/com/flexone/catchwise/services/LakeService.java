package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Lake;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface LakeService {

    Lake findById(Long id);
    List<Lake> findAll();
    List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng);
}
