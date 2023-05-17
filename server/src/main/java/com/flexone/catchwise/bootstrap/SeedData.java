package com.flexone.catchwise.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeDTO;
import com.flexone.catchwise.repositories.FishRepository;
import com.flexone.catchwise.repositories.LakeRepository;
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
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, LakeDTO.class);
        List<LakeDTO> lakesJson = objectMapper.readValue(file, collectionType);
        List<Lake> lakes = new ArrayList<>();
        for (LakeDTO lakeDTO : lakesJson) {
            lakes.add(mapLakeDTOToLake(lakeDTO));
        }
        return lakes;
    }

    private Lake mapLakeDTOToLake(LakeDTO lakeDTO) {
        List<String> speciesList = Arrays.stream(lakeDTO.getFishSpecies()).map(LakeDTO.FishSpecies::getSpecies).filter(species -> species.length() > 0).collect(Collectors.toList());
        List<Fish> fish = fishRepository.findAllBySpeciesIn(speciesList);
        Set<Fish> fishSet = new HashSet<>(fish);

        return Lake.builder()
                .name(lakeDTO.getName())
                .localId(lakeDTO.getLocalId())
                .state(lakeDTO.getState())
                .county(lakeDTO.getCounty())
                .countyId(lakeDTO.getCountyId())
                .nearestTown(lakeDTO.getNearestTown())
                .coordinates(lakeDTO.getCoordinates())
                .fish(fishSet)
                .build();
    }

}
