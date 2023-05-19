package com.flexone.catchwise.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flexone.catchwise.domain.County;
import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeJSON;
import com.flexone.catchwise.repositories.CountyRepository;
import com.flexone.catchwise.repositories.FishRepository;
import com.flexone.catchwise.repositories.LakeRepository;
import com.flexone.catchwise.repositories.StateRepository;
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
    private final CountyRepository countyRepository;
    private final StateRepository stateRepository;

    @Override
    public void run(String... args) {
        seed();
    }

    public void seed() {
        System.out.println("Seeding data...");
        try {
            List<Fish> fishList = importFishDataJSON();
            fishRepository.saveAll(fishList);

            List<Lake> lakeList = importLakeDataJSON();
            lakeRepository.saveAll(lakeList);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Seeding complete.");
        }
    }

    private List<Fish> importFishDataJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/data/fishData.json");
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Fish.class);
        return objectMapper.readValue(file, collectionType);
    }

    private List<Lake> importLakeDataJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/data/lakeData.json");
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, LakeJSON.class);
        List<LakeJSON> lakesJson = objectMapper.readValue(file, collectionType);
        List<Lake> lakes = new ArrayList<>();
        for (LakeJSON lakeJSON : lakesJson) {
            lakes.add(mapLakeJSONToLake(lakeJSON));
        }
        return lakes;
    }

    private Lake mapLakeJSONToLake(LakeJSON lakeJSON) {
        List<String> speciesList = Arrays.stream(lakeJSON.getFishSpecies()).map(LakeJSON.FishSpecies::getSpecies).filter(species -> species.length() > 0).collect(Collectors.toList());
        List<Fish> fish = fishRepository.findAllBySpeciesIn(speciesList);
        Set<Fish> fishSet = new HashSet<>(fish);
        County county = countyRepository.findByName(lakeJSON.getCounty());

        return Lake.builder()
                .name(lakeJSON.getName())
                .localId(lakeJSON.getLocalId())
                .county(county)
                .nearestTown(lakeJSON.getNearestTown())
                .coordinates(lakeJSON.getCoordinates())
                .fish(fishSet)
                .build();
    }

}
