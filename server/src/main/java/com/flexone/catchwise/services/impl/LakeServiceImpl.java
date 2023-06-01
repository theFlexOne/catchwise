package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakeBase;
import com.flexone.catchwise.mapper.FishToLakeFishResponseMapper;
import com.flexone.catchwise.repositories.LakeBaseRepository;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LakeServiceImpl implements LakeService {

    final FishToLakeFishResponseMapper fishMapper;
    final LakeBaseRepository lakeBaseRepository;

    @Override
    public Lake findById(Long id) {
        return (Lake) lakeBaseRepository.findById(id).orElse(null);
    }

    @Override
    public List<LakeBase> findAll() {
        return lakeBaseRepository.findAll();
    }

    @Override
    public List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng) {
        return filterRelevantLakes(lakeBaseRepository.findAllInRange(minLat, maxLat, minLng, maxLng));
    }

    private static List<Lake> filterRelevantLakes(List<Lake> lakes) {
        return lakes.stream()
                .filter((Predicate<? super Lake>) isRelevantLake())
                .map(l -> (Lake) l)
                .collect(Collectors.toList());
    }

    private static Predicate<? super LakeBase> isRelevantLake() {
        return l -> l.getFishes().size() > 0 && l.getCoordinates() != null && l.getName() != null;
    }


}
