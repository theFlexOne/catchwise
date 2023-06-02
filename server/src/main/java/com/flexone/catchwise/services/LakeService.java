package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.mapper.FishToLakeFishResponseMapper;
import com.flexone.catchwise.repositories.LakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LakeService {
    final LakeRepository lakeRepository;

    public Lake findById(Long id) {
        return (Lake) lakeRepository.findById(id).orElse(null);
    }

    public List<Lake> findAll() {
        return lakeRepository.findAll();
    }

    public List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng) {
        return filterRelevantLakes(lakeRepository.findAllInRange(minLat, maxLat, minLng, maxLng));
    }

    private static List<Lake> filterRelevantLakes(List<Lake> lakes) {
        return lakes.stream()
                .filter((Predicate<? super Lake>) isRelevantLake())
                .map(l -> (Lake) l)
                .collect(Collectors.toList());
    }

    private static Predicate<? super Lake> isRelevantLake() {
        return l -> l.getFishes().size() > 0 && l.getCoordinates() != null && l.getName() != null;
    }
}
