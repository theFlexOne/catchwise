package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.mapper.FishToLakeFishResponseMapper;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LakeServiceImpl implements LakeService {

    final FishToLakeFishResponseMapper fishMapper;

    @Override
    public Lake findById(Long id) {
        return null;
    }

    @Override
    public List<Lake> findAll() {
        return null;
    }

    @Override
    public List<Lake> findAllInRange(double minLat, double maxLat, double minLng, double maxLng) {
        return null;
    }

    @Override
    public Lake findByLocalId(String localId) {
        return null; }


//    // region HELPER METHODS
//    private static List<Lake> filterRelevantLakes(List<LakeBase> lakes) {
//        return lakes.stream().filter(isRelevantLake()).map(l -> (Lake) l).collect(Collectors.toList());
//    }
//
//    private static Predicate<LakeBase> isRelevantLake() {
//        return l -> l.getFish().size() > 0 && l.getCoordinates() != null && l.getName() != null;
//    }
//    // endregion

}
