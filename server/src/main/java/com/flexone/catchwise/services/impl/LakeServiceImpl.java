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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LakeServiceImpl implements LakeService {

    final LakeRepository lakeRepository;
    final FishToLakeFishResponseMapper fishMapper;

    @Override
    public Lake findById(Long id) {
        return lakeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lake> findAll() {
        return filterRelevantLakes(lakeRepository.findAll());
    }

    @Override
    public List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng) {
        return filterRelevantLakes(lakeRepository.findAllInRange(minLat, maxLat, minLng, maxLng));
    }

    private static List<Lake> filterRelevantLakes(List<Lake> lakes) {
        return lakes.stream()
                .filter(isRelevantLake())
                .collect(Collectors.toList());
    }

    private static Predicate<Lake> isRelevantLake() {
        return l -> l.getFish().size() > 0 && l.getCoordinates() != null && l.getName() != null;
    }


}
