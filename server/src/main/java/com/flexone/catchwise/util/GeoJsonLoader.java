package com.flexone.catchwise.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwise.bootstrap.dto.GeoJsonFeatureCollection;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class GeoJsonLoader {
    public GeoJsonFeatureCollection readGeoJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String geoJsonData = new String(Files.readAllBytes(Paths.get(filePath)));
        return objectMapper.readValue(geoJsonData, GeoJsonFeatureCollection.class);
    }

}
