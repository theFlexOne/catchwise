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
    private final StateRepository stateRepository;

    @Override
    public void run(String... args) {

//        seed();
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
        HashMap<String, List<LakeJSON>> lakes = new HashMap<>();

        for (LakeJSON lakeJSON : lakesJson) {
        }

        List<Lake> lakeList = new ArrayList<>(lakes.get("lake").stream().map(this::mapLakeJSONToLake).toList());
        lakeList.addAll(lakes.get("other").stream().map(this::mapLakeJSONToLake).toList());
        lakeBaseRepository.saveAll(lakeList);

    }

    private Lake mapLakeJSONToLake(LakeJSON lakeJSON) {
        List<String> speciesList = Arrays.stream(lakeJSON.getFishSpecies()).map(LakeJSON.FishSpecies::getSpecies).filter(species -> species.length() > 0).collect(Collectors.toList());
        List<Fish> fish = fishRepository.findAllBySpeciesIn(speciesList);
        Set<Fish> fishSet = new HashSet<>(fish);

        County county = countyRepository.findById(lakeJSON.getCountyId()).orElseThrow();

        Coordinates coordinates = lakeJSON.getCoordinates();
        if (coordinates.getLatitude() == 0 && coordinates.getLongitude() == 0) coordinates = null;

        String name = lakeJSON.getName();
        if (name.equals("unnamed")) name = null;

        return (Lake) new Lake()
                .setLocalId(lakeJSON.getLocalId())
                .setName(name)
                .setCoordinates(coordinates)
                .setCounty(county)
                .setFish(fishSet);
    }



}
