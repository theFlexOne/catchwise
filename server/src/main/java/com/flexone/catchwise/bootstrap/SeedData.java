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
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedData implements CommandLineRunner {

    private final FishRepository fishRepository;
    private final LakeRepository lakeRepository;
    private final CountyRepository countyRepository;

    final Boolean shouldSeedData = true;

    @Override
    public void run(String... args) {

        if (shouldSeedData) {
            seed();
        } else {
            log.info("Seeding OFF.");
        }


    }

    public void seed() {
        System.out.println("Seeding data...");
        try {
            importFishDataJSON();
            importLakeDataJSON();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Seeding complete.");
        }
    }

    private void importFishDataJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/data/fishData.json");
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Fish.class);
        fishRepository.saveAll(objectMapper.readValue(file, collectionType));
    }

    private void importLakeDataJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, LakeJSON.class);
        File file = new File("src/main/resources/data/lakeData_min.json");
        List<LakeJSON> lakesJson = objectMapper.readValue(file, collectionType);
        HashSet<Lake> lakes = new HashSet<>();
        HashSet<String> notFoundFishSpecies = new HashSet<>();


        for (LakeJSON lakeJSON : lakesJson) {
            Object[] data = mapLakeJSONToLake(lakeJSON);
            lakes.add((Lake) data[0]);
            notFoundFishSpecies.addAll((HashSet<String>) data[1]);
        }

        // print out fish species that were not found in the database
        log.info("Couldn't find the following fish species:");
        for (String species : notFoundFishSpecies) {
            log.info("    --->  " + species);
        }




        lakeRepository.saveAll(lakes);

    }

    private Object[] mapLakeJSONToLake(LakeJSON lakeJSON) {
        List<String> speciesList = Arrays.stream(lakeJSON.getFish()).map(LakeJSON.FishSpecies::getSpecies).collect(Collectors.toList());
        HashSet<Fish> fishes = new HashSet<>(fishRepository.findAllBySpeciesIn(speciesList));

        // merge 'speciesList' and 'fishes' into one HashSet
        HashSet<String> notFoundFishSpecies = new HashSet<>();
        for (String species : speciesList) {
            if (fishes.stream().noneMatch(fish -> fish.getSpecies().equals(species))) {
                Fish fish = new Fish().setSpecies(species);
                fishes.add(fish);
                notFoundFishSpecies.add(species);
            }
        }


        Lake lake = new Lake()
                .setName(lakeJSON.getName())
                .setLocalId(lakeJSON.getLocalId())
                .setCounty(lakeJSON.getCountyId() == null ? null : countyRepository.findById(lakeJSON.getCountyId()).orElse(null))
                .setNearestTown(lakeJSON.getNearestTown())
                .setCoordinates(lakeJSON.getCoordinates())
                .setFish(fishes);

        for (LakeJSON.Component lakeComponent : lakeJSON.getComponents()) {
            speciesList = Arrays.stream(lakeComponent.getFish()).map(LakeJSON.FishSpecies::getSpecies).collect(Collectors.toList());
            HashSet<Fish> lcFishes = new HashSet<>(fishRepository.findAllBySpeciesIn(speciesList));
            LakeComponent component = new LakeComponent()
                    .setLocalId(lakeComponent.getLocalId())
                    .setName(lakeComponent.getName())
                    .setCoordinates(lakeComponent.getCoordinates())
                    .setFish(lcFishes);
            lake.getLakeComponents().add(component);
        }

//        Resource lakeResource = new Resource()
//                .setLocalId(lakeJSON.getLocalId())
//                .setDescription("MN DNR Lake Information")
//                .setUrl("https://www.dnr.state.mn.us/lakefind/lake.html?id=" + lakeJSON.getLocalId());

        return new Object[]{
                lake,
                notFoundFishSpecies
        };
    }



}
