package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeResponse;
import com.flexone.catchwise.mapper.FishToLakeFishResponseMapper;
import com.flexone.catchwise.repositories.LakeRepository;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Page<LakeResponse> findAll(int page, int size, Sort.Direction direction, String sortProperty) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));
        Page<Lake> allLakes = lakeRepository.findAll(pageable);
        return allLakes.map(this::mapLakeToLakeResponse);
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
