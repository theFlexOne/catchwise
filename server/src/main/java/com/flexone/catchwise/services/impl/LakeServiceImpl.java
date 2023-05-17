package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeFishResponse;
import com.flexone.catchwise.dto.LakeResponse;
import com.flexone.catchwise.mapper.FishToLakeFishResponseMapper;
import com.flexone.catchwise.repositories.LakeRepository;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LakeServiceImpl  implements LakeService {

    final LakeRepository lakeRepository;
    final FishToLakeFishResponseMapper fishMapper;

    @Override
    public LakeResponse getLakeById(Long id) {
        Lake lake = lakeRepository.findById(id).orElse(null);
        if (lake == null) {
            return null;
        }
        return mapLakeToLakeResponse(lake);
    }

    @Override
    public List<Lake> getLakes() {
        return (List<Lake>) lakeRepository.findAll();
    }

    private LakeResponse mapLakeToLakeResponse(Lake lake) {
        return new LakeResponse(
                lake.getId(),
                lake.getName(),
                lake.getLocalId(),
                lake.getState(),
                lake.getCounty(),
                lake.getCountyId(),
                lake.getNearestTown(),
                lake.getCoordinates(),
                lake.getFish().stream()
                        .map(fishMapper::fishToLakeFishResponse)
                        .collect(Collectors.toList())
        );
    }
}
