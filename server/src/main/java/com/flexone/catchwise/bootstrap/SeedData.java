package com.flexone.catchwise.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flexone.catchwise.bootstrap.dto.*;
import com.flexone.catchwise.domain.*;
import com.flexone.catchwise.repository.*;
import com.flexone.catchwise.service.GeoDataService;
import com.flexone.catchwise.util.GeoJsonLoader;
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

    final GeoJsonLoader geoJsonLoader;
    final GeoDataService geoDataService;

    @Override
    public void run(String... args) throws Exception {

        GeoJsonFeatureCollection geoJson = geoJsonLoader.readGeoJsonFile("src/main/resources/data/US_States.geojson");
        geoDataService.saveGeoJsonFeatures(geoJson);

    }
}
