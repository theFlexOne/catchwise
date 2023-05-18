package com.flexone.catchwise.controllers;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeResponse;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lakes")
@RequiredArgsConstructor
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
                .fish(lake.getLakeFishResponses())
                .build();
    }

    @GetMapping
    public Page<LakeResponse> getAllLakes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String sortProperty //? <- could create an Enum for sortProperty
    ) {
        Page<Lake> lakes = lakeService.findAll(page, size, direction, sortProperty);
        return lakes.map(LakeController::mapLakeToLakeResponse);
    }

    @GetMapping("/{id}")
    public LakeResponse getLakeById(@PathVariable Long id) {
        Lake lake = lakeService.findById(id);
        return mapLakeToLakeResponse(lake);
    }
}
