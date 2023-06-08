package com.flexone.catchwise.service;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.repository.LakeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LakeService {
    final LakeRepository lakeRepository;

    public Lake findById(Long id) {
        return (Lake) lakeRepository.findById(id).orElse(null);
    }

    public List<Lake> findAll() {
        return lakeRepository.findAll();
    }

    public List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng) {
        log.info("Finding all lakes in range in database");
        List<Lake> lakes = lakeRepository.findAllInRange(minLat, maxLat, minLng, maxLng);
        log.info("Filtering relevant lakes");
        lakes = filterRelevantLakes(lakes);
        return lakes;
    }

    public void saveAll(List<Lake> lakes) {
        lakeRepository.saveAll(lakes);
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
