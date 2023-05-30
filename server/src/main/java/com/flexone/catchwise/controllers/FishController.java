package com.flexone.catchwise.controllers;

import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.dto.FishResponse;
import com.flexone.catchwise.services.FishService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fish")
@RequiredArgsConstructor
public class FishController {

    final FishService fishService;

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
    public Page<FishResponse> getAllFish(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String sortProperty //? could create an enum for sortProperty
    ) {
        Page<Fish> fish = fishService.findAll(page, size, direction, sortProperty);
        return fish.map(FishController::mapFishToFishResponse);
    }

    @GetMapping("/{id}")
    public FishResponse getFishById(@PathVariable Long id) {
        Fish fish = fishService.findById(id);
        return mapFishToFishResponse(fish);
    }

    @GetMapping("/species/{species}")
    public FishResponse getFishBySpecies(@PathVariable String species) {
        Fish fish = fishService.findBySpecies(species);
        return mapFishToFishResponse(fish);
    }
}
