package com.flexone.catchwise.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexone.catchwise.bootstrap.dto.GeoJsonFeatureCollection;
import lombok.RequiredArgsConstructor;
import org.geojson.FeatureCollection;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class GeoJsonLoader {

    private final ObjectMapper objectMapper;
    public GeoJsonFeatureCollection readGeoJsonFile(String filePath) throws IOException {
        String geoJsonData = new String(Files.readAllBytes(Paths.get(filePath)));
        return objectMapper.readValue(geoJsonData, GeoJsonFeatureCollection.class);
    }

}
