package com.flexone.catchwise.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwise.domain.County;
import com.flexone.catchwise.domain.GeoData;
import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.State;
import com.flexone.catchwise.repository.StateRepository;
import com.flexone.catchwise.service.LakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedData implements CommandLineRunner {

  private final StateRepository stateRepository;
  private final LakeService lakeService;
  private final GeoJSONReader reader = new GeoJSONReader();

  @Override
  public void run(String... args) throws Exception {
    log.info("Seeding data...");
    try {
      log.info("Loading States data...");
      seedStates("src/main/resources/data/US_States.geojson");
      log.info("Loading Lakes data...");
      seedMNLakes("src/main/resources/data/MN_Waterbodies_Named.geojson");
    } catch (IOException e) {
      e.printStackTrace();
      log.info("**SEEDING FAILED**");
    }
    log.info("Seeding complete!");

  }

  private void seedMNLakes(String pathName) throws IOException {
    FeatureCollection lakesFeatureCollection = importFeatureCollection(pathName);
    Feature[] features = lakesFeatureCollection.getFeatures();
    List<Lake> lakesList = new ArrayList<>();
    for (Feature feature : features) {
      Map<String, Object> properties = feature.getProperties();
      Geometry geometry = reader.read(feature.getGeometry());
      Lake newLake = new Lake()
              .setName((String) properties.get("gnis_name"))
              .setGeoData(new GeoData().setGeometry(geometry).setProperties(new ObjectMapper().writeValueAsString(properties)));
      lakesList.add(newLake);
    }
    log.info("Saving lakes to database...");
    lakeService.saveAll(lakesList);
  }

  private void seedStates(String pathName) throws IOException {
    FeatureCollection statesFeatureCollection = importFeatureCollection(pathName);
    FeatureCollection countyFeatureCollection = importFeatureCollection("src/main/resources/data/US_Counties.geojson");

    Feature[] features = statesFeatureCollection.getFeatures();
    List<State> statesList = new ArrayList<>();
    for (Feature feature : features) {
      Map<String, Object> properties = feature.getProperties();
      Geometry geometry = reader.read(feature.getGeometry());
      List<County> countyList = new ArrayList<>();
      for (Feature countyFeature : countyFeatureCollection.getFeatures()) {
        Map<String, Object> countyProperties = countyFeature.getProperties();
        Geometry countyGeometry = reader.read(countyFeature.getGeometry());
        if (feature.getProperties().get("STATEFP").equals(countyProperties.get("STATEFP"))) {
          County newCounty = new County()
                  .setName((String) countyProperties.get("NAME"))
                  .setFipsCode((String) countyProperties.get("COUNTYFP"))
                  .setNsCode((String) countyProperties.get("COUNTYNS"))
                  .setGeoData(new GeoData().setGeometry(countyGeometry).setProperties(new ObjectMapper().writeValueAsString(countyProperties)));
          countyList.add(newCounty);
        }
      }
      State newState = new State()
              .setName((String) properties.get("NAME"))
              .setAbbr((String) properties.get("STUSPS"))
              .setFipsCode((String) properties.get("STATEFP"))
              .setAnsiCode((String) properties.get("STATENS"))
              .setGeoData(new GeoData()
                      .setGeometry(geometry)
                      .setProperties(new ObjectMapper().writeValueAsString(properties)))
              .addCounties(countyList);

      statesList.add(newState);
    }
    log.info("Saving States data...");
    stateRepository.saveAll(statesList);
  }

  private FeatureCollection importFeatureCollection(String pathName) throws IOException {
    return new ObjectMapper().readValue(Paths.get(pathName).toFile(), FeatureCollection.class);
  }
}
