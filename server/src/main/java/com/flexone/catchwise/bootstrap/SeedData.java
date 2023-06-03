package com.flexone.catchwise.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flexone.catchwise.domain.*;
import com.flexone.catchwise.dto.LakeJSON;
import com.flexone.catchwise.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedData implements CommandLineRunner {

    private final FishRepository fishRepository;
    private final LakeRepository lakeRepository;
    private final LakeComponentRepository lakeComponentRepository;
    private final CountyRepository countyRepository;

    @Override
    public void run(String... args) {

//        seed();
    }

    public void seed() {
        System.out.println("Seeding data...");
        try {
            log.info("Seeding Fish data...");
            importFishDataJSON();
            log.info("Seeding Fish data complete.");
            log.info("Seeding Lake data...");
            importLakeDataJSON();
            log.info("Seeding Lake data complete.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Seeding complete.");
        }
    }

    private void importFishDataJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Importing fish data JSON...");
        File file = new File("src/main/resources/data/fishData.json");
        log.info("Mapping JSON to Fish objects...");
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Fish.class);
        log.info("Saving Fish objects to database...");
        fishRepository.saveAll(objectMapper.readValue(file, collectionType));
    }

    private void importLakeDataJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Importing lake data JSON...");
        File file = new File("src/main/resources/data/lakeData.json");
        log.info("Mapping JSON to LakeJSON objects...");
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, LakeJSON.class);
        List<LakeJSON> lakes = objectMapper.readValue(file, collectionType);
        log.info("Saving LakeJSON objects to Lake objects...");
        List<Lake> lakeList = lakes.stream().map(this::mapLakeJSONToLake).toList();
        log.info("Saving LakeJSON objects to database...");
        lakeRepository.saveAll(lakeList);
    }

    private Lake mapLakeJSONToLake(LakeJSON lakeJSON) {

        County lakeCounty = countyRepository.findById(lakeJSON.getCountyId()).orElseThrow();

        HashSet<Fish> lakeFishes = lakeJSON.getFishes().stream()
                .map(this::findOrCreateFish)
                .collect(Collectors.toCollection(HashSet::new));

        List<LakeComponent> lakeComponents = lakeJSON.getComponents().stream()
                .map(lc -> {
                    HashSet<Fish> componentFishes = lc.getFishes().stream()
                            .map(this::findOrCreateFish)
                            .collect(Collectors.toCollection(HashSet::new));

                    return new LakeComponent()
                            .setName(lc.getName())
                            .setLocalId(lc.getLocalId())
                            .setCoordinates(lc.getCoordinates())
                            .setFishes(componentFishes);
                }).collect(Collectors.toList());


        return new Lake()
                .setName(lakeJSON.getName())
                .setLocalId(lakeJSON.getLocalId())
                .setNearestTown(lakeJSON.getNearestTown())
                .setCoordinates(lakeJSON.getCoordinates())
                .setCounty(lakeCounty)
                .setFishes(lakeFishes)
                .setComponents(lakeComponents);
    }


    private Fish findOrCreateFish(LakeJSON.FishSpecies fish) {
        Fish newFish = fishRepository.findBySpecies(fish.getSpecies()).orElse(null);
        if (newFish == null) {
            newFish = new Fish()
                    .setName(fish.getName())
                    .setSpecies(fish.getSpecies());
        }
        return fishRepository.save(newFish);
    }
}
