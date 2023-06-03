package com.flexone.catchwise.controllers;

import com.flexone.catchwise.domain.County;
import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.domain.Lake;
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

    private static Predicate<Lake> isRelevantLake() {
        return l -> l.getFishes().size() > 0 && l.getCoordinates() != null && l.getName() != null;
    }


    @GetMapping
    public List<LakeResponse> getAllLakes() {
        List<Lake> lakes = lakeService.findAll();
        log.info("Found {} lakes", lakes.size());
        return lakes.stream()
                .filter(isRelevantLake())
                .map(LakeController::mapLakeToLakeResponse)
                .toList();
    }

    private static LakeResponse mapLakeToLakeResponse(Lake l) {
        return new LakeResponse()
                .setId(l.getId())
                .setName(l.getName())
                .setLocalId(l.getLocalId())
                .setCounty(l.getCounty().getName())
                .setState(l.getCounty().getState().getName())
                .setNearestTown(l.getNearestTown())
                .setCoordinates(l.getCoordinates())
                .setFishesUrl(l.buildLakeFishUrl());
    }

//    @GetMapping("/in-range")
//    public List<LakeResponse> getAllLakesInRange(
//            @RequestParam double minLat,
//            @RequestParam double maxLat,
//            @RequestParam double minLng,
//            @RequestParam double maxLng
//    ) {
//
//
////        log.info("Searching for lakes in range: " + minLat + " " + maxLat + " " + minLng + " " + maxLng);
////        List<Lake> lakes = lakeService.findAllInRange(minLat, maxLat, minLng, maxLng);
////        log.info("Found {} lakes in range", lakes.size());
////        log.info("Mapping lakes to responses");
////        List<LakeResponse> responses = lakes.stream().map(LakeController::mapLakeToLakeResponse).toList();
////        log.info("Returning {} lake responses", responses.size());
////        return responses;
//    }

    @GetMapping("/names")
    public List<LakeNameResponse> getAllLakeNames() {
        List<Lake> lakes = lakeService.findAll();
        log.info("Found {} lakes", lakes.size());
        return lakes.stream().map(l -> new LakeNameResponse(l.getId(), l.getName())).toList();
    }

    @GetMapping("/{id}")
    public LakeResponse getLakeById(@PathVariable Long id) {
        Lake lake = lakeService.findById(id);
        return mapLakeToLakeResponse(lake);
    }

    @GetMapping("/{id}/fish")
    public List<FishResponse> getLakeFishById(@PathVariable Long id) {
        Lake lake = lakeService.findById(id);
        return lake.getFishes().stream().map(LakeController::mapFishToFishResponse).toList();
    }

    private static FishResponse mapFishToFishResponse(Fish fish) {
        return new FishResponse()
                .setId(fish.getId())
                .setName(fish.getName())
                .setSpecies(fish.getSpecies())
                .setDescription(fish.getDescription())
                .setIdentification(fish.getIdentification())
                .setCommonNames(fish.getCommonNames());
    }
}
