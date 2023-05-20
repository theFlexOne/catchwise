package com.flexone.catchwise.controllers;

import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.StaticMapsRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/maps")
@Slf4j
public class MapsController {

    private final String GOOGLE_MAPS_API_KEY = "AIzaSyAJAJsv37lGLQD9EJqsvfHe-Fi_NzzNOlc";
    private final GeoApiContext context = new GeoApiContext.Builder().apiKey(GOOGLE_MAPS_API_KEY).build();

    @GetMapping
    public ResponseEntity<byte[]> getMap(
            @RequestParam("lat") Double lat,
            @RequestParam("lon") Double lon,
            @RequestParam(value = "zoom", defaultValue = "10", required = false) Integer zoom,
            @RequestParam(value = "size", defaultValue = "600x300", required = false) String size) throws IOException, InterruptedException, ApiException {

        LatLng location = new LatLng(lat, lon);

        String[] sizeArr = size.split("x");
        Size mapSize = new Size(Integer.parseInt(sizeArr[0]), Integer.parseInt(sizeArr[1]));

        StaticMapsRequest request = new StaticMapsRequest(context)
                .center(location)
                .zoom(zoom)
                .size(mapSize)
                .scale(2);

        ImageResult imageResult = request.await();

        return ResponseEntity.ok(imageResult.imageData);

    }

}
