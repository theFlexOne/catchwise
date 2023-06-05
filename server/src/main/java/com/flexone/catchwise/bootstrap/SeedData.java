package com.flexone.catchwise.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flexone.catchwise.bootstrap.json.CountyJSON;
import com.flexone.catchwise.bootstrap.json.FishJSON;
import com.flexone.catchwise.bootstrap.json.StateJSON;
import com.flexone.catchwise.domain.*;
import com.flexone.catchwise.bootstrap.json.LakeJSON;
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
    private final StateRepository stateRepository;

    @Override
    public void run(String... args) {
<<<<<<< HEAD

=======
>>>>>>> 704d6b66c9d39fa127599ed7bea093d15f86b9d3
//        seed();
    }

    public void seed() {
        log.info("Seeding data...");
        try {
            log.info("Seeding Location data...");
            seedLocationData();
            log.info("Seeding Fish data...");
            seedFishData();
            log.info("Seeding Lake data...");
            seedLakeData();
            log.info("Seeding complete.");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("**SEEDING FAILED**");
        }
    }

    private void seedLocationData() throws IOException {
        int countyCount = (int) countyRepository.count();
        int stateCount = (int) stateRepository.count();
        if (countyCount > 0 || stateCount > 0) {
            log.info("Location data already exists. Skipping seeding.");
            return;
        }


        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data/statesWithCountiesGeoData.json");
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, StateJSON.class);
        List<StateJSON> statesData = mapper.readValue(file, type);
        List<State> states = mapStateJSONToState(statesData);
        stateRepository.saveAll(states);
    }


    private void seedFishData() throws IOException {
        int fishCount = (int) fishRepository.count();

        if (fishCount > 0) {
            log.info("Fish data already exists. Skipping seeding.");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/data/fishData.json");
        CollectionType collectionType = objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, FishJSON.class);
        List<FishJSON> fishJSONList = objectMapper.readValue(file, collectionType);
        List<Fish> fishList = fishJSONList.stream()
                .map(this::mapFishJSONToFish)
                .toList();
        fishRepository.saveAll(fishList);
    }

    private void seedLakeData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("src/main/resources/data/lakeData.json");

        CollectionType collectionType = objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, LakeJSON.class);
        List<LakeJSON> lakes = objectMapper.readValue(file, collectionType);

        List<Lake> lakeList = lakes.stream()
                .map(this::mapLakeJSONToLake)
                .toList();

        lakeRepository.saveAll(lakeList);

    }


    private Fish mapFishJSONToFish(FishJSON fishJSON) {
        return new Fish()
                .setName(fishJSON.getName())
                .setSpecies(fishJSON.getSpecies())
                .setFamily(fishJSON.getFamily())
                .setDescription(fishJSON.getDescription())
                .setIdentification(fishJSON.getIdentification())
                .setCommonNames(fishJSON.getCommonNames());
    }


    private Lake mapLakeJSONToLake(LakeJSON lakeJSON) {

        County lakeCounty = countyRepository.findByGeoDataGeoId(lakeJSON.getCountyFips())
                .orElseThrow(() -> new RuntimeException("County not found for lake: " + lakeJSON.getName()));

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

    private List<State> mapStateJSONToState(List<StateJSON> statesData) {
        return statesData.stream()
                .map(stateJSON -> {

                    return new State()
                            .setName(stateJSON.getName())
                            .setAbbr(stateJSON.getStusps())
                            .setGeoData(new GeoData()
                                    .setFipsId(stateJSON.getStatefp())
                                    .setGnisId(stateJSON.getStatens())
                                    .setGeoId(stateJSON.getGeoid())
                                    .setAmericanFactFinderId(stateJSON.getAffgeoid())
                                    .setLsad(stateJSON.getLsad())
                                    .setLandArea(stateJSON.getAland())
                                    .setWaterArea(stateJSON.getAwater())
                                    .setGeometry(stateJSON.getGeometry())
                            )
                            .addCounties(mapCountyJSONToCounty(stateJSON.getCounties()));
                })
                .collect(Collectors.toList());
    }

    private List<County> mapCountyJSONToCounty(CountyJSON[] counties) {
        return Arrays.stream(counties)
                .map(countyJSON -> {
                    return new County()
                            .setName(countyJSON.getName())
                            .setGeoData(new GeoData()
                                    .setFipsId(countyJSON.getCountyfp())
                                    .setGnisId(countyJSON.getCountyns())
                                    .setGeoId(countyJSON.getGeoid())
                                    .setAmericanFactFinderId(countyJSON.getAffgeoid())
                                    .setLsad(countyJSON.getLsad())
                                    .setLandArea(countyJSON.getAland())
                                    .setWaterArea(countyJSON.getAwater())
                                    .setGeometry(countyJSON.getGeometry())
                            );
                })
                .collect(Collectors.toList());
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
