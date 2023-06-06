package com.flexone.catchwise.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/google")
public class GoogleApiController {

    final WebClient webClient = WebClient.create();
    final String placesSearchUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext";
    final String placesDetailsUrl = "https://maps.googleapis.com/maps/api/place/details";
    final String placesPhotoUrl = "https://maps.googleapis.com/maps/api/place/photo";
    final String elevationUrl = "https://maps.googleapis.com/maps/api/elevation";
    final String reverseGeocodeUrl = "https://maps.googleapis.com/maps/api/geocode";

    @Value("${google.api.key}")
    String googleApiKey;

    @GetMapping("/place/search")
    public ResponseEntity<Object> getPlaceSearch(
            @RequestParam String query,
            @RequestParam(required = false, name = "inputtype") String inputType,
            @RequestParam(required = false) String output,
            @RequestParam(required = false) String... fields
    ) {
        log.info("fields: " + Arrays.toString(fields));
        Object response = useGooglePlaceSearchApi(query, fields, inputType, output);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/place/details")
    public ResponseEntity<Object> getPlaceDetails(
            @RequestParam(name = "place_id") String placeId,
            @RequestParam(required = false) String output,
            @RequestParam(required = false) String... fields
    ) {
        log.info("fields: " + Arrays.toString(fields));
        Object response = useGooglePlaceDetailsApi(placeId, output, fields);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/place/photo")
    public ResponseEntity<Object> getPlacePhoto(
            @RequestParam(name = "photo_reference") String photoReference,
            @RequestParam(required = false) String maxwidth,
            @RequestParam(required = false) String maxheight
    ) {
        Object response = useGooglePlacePhotoApi(photoReference, maxwidth, maxheight);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/elevation")
    public ResponseEntity<Object> getElevation(
            @RequestParam Double lat,
            @RequestParam Double lng
    ) {
        String locations = lat + "," + lng;
        Object response = useGoogleElevationApi(locations);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reverse-geocode")
    public ResponseEntity<Object> getReverseGeocode(
            @RequestParam Double lat,
            @RequestParam Double lng
    ) {
        String locations = lat + "," + lng;
        Object response = useGoogleReverseGeocodeApi(locations);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }


    // region METHODS

    private Object useGooglePlaceSearchApi(String query, String[] fields, String inputType, String output) {
        inputType = inputType == null ? "textquery" : inputType;
        output = output == null ? "json" : output;
        fields = fields.length == 0 ? new String[]{"name", "place_id"} : fields;
        String url = placesSearchUrl +
                "/" + output +
                "?input=" + query +
                "&inputtype=" + inputType +
                "&fields=" + String.join(",", fields) +
                "&key=" + googleApiKey;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    private Object useGooglePlaceDetailsApi(String placeId, String output, String[] fields) {
        output = output == null ? "json" : output;
        fields = fields == null || fields.length == 0 ? new String[]{"name", "place_id"} : fields;
        String url = placesDetailsUrl +
                "/" + output +
                "?place_id=" + placeId +
                "&fields=" + String.join(",", fields) +
                "&key=" + googleApiKey;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    private byte[] useGooglePlacePhotoApi(String photoReference, String maxwidth, String maxheight) {
        maxwidth = maxwidth == null ? "400" : maxwidth;

        String url = placesPhotoUrl +
                "?photo_reference=" + photoReference +
                "&maxwidth=" + maxwidth +
                "&key=" + googleApiKey;

        // fetch the photo from Google and respond with the byte array
        return webClient.get()
                .uri(url)
                .accept(MediaType.IMAGE_JPEG)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    private Object useGoogleElevationApi(String locations, String output) {
        output = output == null ? "json" : output;
        String url = elevationUrl +
                "/" + output +
                "?locations=" + locations +
                "&key=" + googleApiKey;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
    private Object useGoogleElevationApi(String locations) {
        return useGoogleElevationApi(locations, "json");
    }

    private Object useGoogleReverseGeocodeApi(String locations, String output) {
        String url = reverseGeocodeUrl +
                "/" + output +
                "?latlng=" + locations +
                "&location_type=GEOMETRIC_CENTER" +
                "&key=" + googleApiKey;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
    private Object useGoogleReverseGeocodeApi(String locations) {
        return useGoogleReverseGeocodeApi(locations, "json");
    }

    // endregion

}

