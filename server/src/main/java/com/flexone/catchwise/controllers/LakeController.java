package com.flexone.catchwise.controllers;

import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.FishResponse;
import com.flexone.catchwise.dto.LakeResponse;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lakes")
@RequiredArgsConstructor
@Slf4j
public class LakeController {

    final LakeService lakeService;

    private static LakeResponse mapLakeToLakeResponse(Lake lake) {
        return LakeResponse.builder()
                .id(lake.getId())
                .name(lake.getName())
                .localId(lake.getLocalId())
                .county(lake.getCounty().getName())
                .state(lake.getCounty().getState().getName())
                .nearestTown(lake.getNearestTown())
                .coordinates(lake.getCoordinates())
                .fishUrl(lake.getLakeFishUrl())
                .build();
    }

    private static FishResponse mapFishToFishResponse(Fish f) {
        return FishResponse.builder()
                .id(f.getId())
                .name(f.getName())
                .family(f.getFamily())
                .species(f.getSpecies())
                .description(f.getDescription())
                .identification(f.getIdentification())
                .commonNames(f.getCommonNames())
                .build();
    }

    @GetMapping
    public List<LakeResponse> getAllLakes() {
        List<Lake> lakes = lakeService.findAll();
        return lakes.stream().map(LakeController::mapLakeToLakeResponse).toList();
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

    @GetMapping("/{id}")
    public LakeResponse getLakeById(@PathVariable Long id) {
        Lake lake = lakeService.findById(id);
        return mapLakeToLakeResponse(lake);
    }

    @GetMapping("/{id}/fish")
    public List<FishResponse> getLakeFishById(@PathVariable Long id) {
        Lake lake = lakeService.findById(id);
        return lake.getFish().stream().map(LakeController::mapFishToFishResponse).toList();
    }
}
