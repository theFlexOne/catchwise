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
    private final LakeBaseRepository lakeBaseRepository;
    private final CountyRepository countyRepository;

    @Override
    public void run(String... args) {

        seed();
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
        File file = new File("src/main/resources/data/lakeData.json");
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, LakeJSON.class);
        List<LakeJSON> lakesJson = objectMapper.readValue(file, collectionType);

        List<Lake> lakes = lakesJson.stream()
                .map(this::mapLakeJSONToLake)
                .toList();




        lakeBaseRepository.saveAll(lakes);

    }

    private Lake mapLakeJSONToLake(LakeJSON lakeJSON) {

        County county = countyRepository.findById(lakeJSON.getCountyId()).orElse(null);
        Coordinates coordinates = lakeJSON.getCoordinates();
        HashSet<Fish> fishSet = Arrays.stream(lakeJSON.getFish())
                .map(fishJSON -> fishRepository.findBySpecies(fishJSON.getSpecies()))
                .collect(Collectors.toCollection(HashSet::new));

        Set<LakePart> lakeParts = Arrays.stream(lakeJSON.getComponents())
                .map(this::mapLakeJSONToLakePart)
                .collect(Collectors.toSet());


        Lake lake = (Lake) new Lake()
                .setLocalId(lakeJSON.getLocalId())
                .setName(lakeJSON.getName())
                .setCoordinates(coordinates)
                .setCounty(county)
                .setFishes(fishSet);

        lake.setLakeParts(lakeJSON.getComponents().length > 0 ? lakeParts : null);
        return lake;
    }

    private LakePart mapLakeJSONToLakePart(LakeJSON lakeJSON) {
        return (LakePart) new LakePart()
                .setLocalId(lakeJSON.getLocalId())
                .setName(lakeJSON.getName())
                .setCoordinates(lakeJSON.getCoordinates());
    }

}
