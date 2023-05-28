package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.mapper.FishToLakeFishResponseMapper;
import com.flexone.catchwise.repositories.LakeRepository;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LakeServiceImpl  implements LakeService {

    final LakeRepository lakeRepository;
    final FishToLakeFishResponseMapper fishMapper;

    @Override
    public Lake findById(Long id) {
        return lakeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lake> findAll() {
        return lakeRepository.findAll();
    }

    @Override
    public List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng) {
        return lakeRepository.findAllInRange(minLat, maxLat, minLng, maxLng);
    }

    @Override
    public List<Lake> findAllWithFish() {
        return lakeRepository.findAllWithFish();

    }

}
