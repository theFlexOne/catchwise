package com.flexone.catchwise.bootstrap;

import com.flexone.catchwise.services.FishService;
import com.flexone.catchwise.services.LakeService;
import org.springframework.stereotype.Component;

@Component
public class SeedData {

    final FishService fishService;
    final LakeService lakeService;

    public SeedData(FishService fishService, LakeService lakeService) {
        this.fishService = fishService;
        this.lakeService = lakeService;
    }

    public void seed() {
        System.out.println("Seeding data...");


    }

}
