package com.flexone.catchwise.controllers;

import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakeBase;
import com.flexone.catchwise.dto.FishResponse;
import com.flexone.catchwise.dto.LakeNameResponse;
import com.flexone.catchwise.dto.LakeResponse;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/lakes")
@RequiredArgsConstructor
@Slf4j
public class LakeController {

    final LakeService lakeService;

    private static Predicate<? super LakeBase> isRelevantLake() {
        return l -> l.getFishes().size() > 0 && l.getCoordinates() != null && l.getName() != null;
    }


    @GetMapping
    public List<LakeResponse> getAllLakes() {
        List<LakeBase> lakes = lakeService.findAll();
        return lakes.stream().map(LakeController::mapLakeToLakeResponse).toList();
    }

    private static LakeResponse mapLakeToLakeResponse(LakeBase lakeBase) {
        return new LakeResponse()
                .setId(lakeBase.getId())
                .setName(lakeBase.getName())
                .setLocalId(lakeBase.getLocalId())
                .setCounty(lakeBase.getCounty().getName())
                .setState(lakeBase.getCounty().getState().getName())
                .setNearestTown(lakeBase.getNearestTown())
                .setCoordinates(lakeBase.getCoordinates())
                .setFishesUrl(lakeBase.buildLakeFishUrl())
                .setLakePartsUrl(lakeBase.buildLakePartsUrl()
        );
    }

    @GetMapping("/in-range")
    public List<LakeResponse> getAllLakesInRange(
            @RequestParam double minLat,
            @RequestParam double maxLat,
            @RequestParam double minLng,
            @RequestParam double maxLng
    ) {
        List<Lake> lakes = lakeService.findAllInRange(minLat, maxLat, minLng, maxLng);
        log.info(minLat + " " + maxLat + " " + minLng + " " + maxLng);
        log.info("Found {} lakes in range", lakes.size());
        return lakes.stream().map(LakeController::mapLakeToLakeResponse).toList();
    }

//    @GetMapping("/names")
//    public List<LakeNameResponse> getAllLakeNames() {
//        List<Lake> lakes = lakeService.findAll();
//        return lakes.stream().map(l -> new LakeNameResponse(l.getId(), l.getName())).toList();
//    }
//
//    @GetMapping("/{id}")
//    public LakeResponse getLakeById(@PathVariable Long id) {
//        Lake lake = lakeService.findById(id);
//        return mapLakeToLakeResponse(lake);
//    }
//
//    @GetMapping("/{id}/fish")
//    public List<FishResponse> getLakeFishById(@PathVariable Long id) {
//        Lake lake = lakeService.findById(id);
//        return lake.getFishes().stream().map(LakeController::mapFishToFishResponse).toList();
//    }
}
