package com.flexone.catchwise.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.repositories.FishRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    private final FishRepository fishRepository;

    public SeedData(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    @Override
    public void run(String... args) {
        seed();
    }

    public void seed() {
        System.out.println("Seeding data...");
        try {
            List<Fish> fishList = importFishDataJSON("src/main/resources/fishData.json");
            fishRepository.saveAll(fishList);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Seeding complete.");
        }
    }

    private List<Fish> importFishDataJSON(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Fish.class);
        return objectMapper.readValue(file, collectionType);
    }

}
