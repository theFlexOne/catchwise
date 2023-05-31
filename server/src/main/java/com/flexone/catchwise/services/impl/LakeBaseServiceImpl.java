package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakeBase;
import com.flexone.catchwise.mapper.FishToLakeFishResponseMapper;
import com.flexone.catchwise.repositories.LakeBaseRepository;
import com.flexone.catchwise.services.LakeBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LakeBaseServiceImpl implements LakeBaseService {

    final LakeBaseRepository lakeBaseRepository;
    final FishToLakeFishResponseMapper fishMapper;

    @Override
    public LakeBase findById(Long id) {
        return lakeBaseRepository.findById(id).orElse(null);
    }

    @Override
    public List<LakeBase> findAll() {
        return filterRelevantLakes(lakeBaseRepository.findAll());
    }

    @Override
    public List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng) {
        return ((Lake) filterRelevantLakes(lakeBaseRepository.findAllInRange(minLat, maxLat, minLng, maxLng)));
    }

    @Override
    public LakeBase findByLocalId(String parentLocalId) {
        return lakeBaseRepository.findByLocalId(parentLocalId);
    }

    private static List<LakeBase> filterRelevantLakes(List<Lake> lakes) {
        return lakes.stream()
                .filter(isRelevantLake())
                .collect(Collectors.toList());
    }

    private static Predicate<LakeBase> isRelevantLake() {
        return l -> l.getFish().size() > 0 && l.getCoordinates() != null && l.getName() != null;
    }


}
