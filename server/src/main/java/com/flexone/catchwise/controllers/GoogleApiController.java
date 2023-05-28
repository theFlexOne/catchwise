package com.flexone.catchwise.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


@RestController
@RequestMapping("/api/v1/google")
@Slf4j
@RequiredArgsConstructor
public class GoogleApiController {

    final Environment env;
    final WebClient webClient = WebClient.create();
    final String placesSearchUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json";
    final String placesDetailsUrl = "https://maps.googleapis.com/maps/api/place/details/json";

    @GetMapping("/place/search")
    public ResponseEntity<Object> getPlaceDetails(@RequestParam String query) {
        Object response = useGooglePlaceSearchApi(query);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/place/details")
    public ResponseEntity<Object> getPlaceDetails(@RequestParam String placeId, @RequestParam(required = false) String... fields) {
        Object response = useGooglePlaceDetailsApi(placeId);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }


    private Object useGooglePlaceSearchApi(String query, String... fields) {
        String inputType = "textquery";
        fields = fields.length == 0 ? new String[]{"name", "place_id"} : fields;
        String url = placesSearchUrl + "?input=" + query +
                "&inputtype=" + inputType +
                "&fields=" + String.join(",", fields) +
                "&key=" + env.getProperty("GOOGLE_API_KEY");

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        /*  expected response:
        {
            "candidates": [
                {
                    "formatted_address": "Lake Nokomis, Minneapolis, MN 55417, USA",
                    "name": "Lake Nokomis",
                    "place_id": "ChIJ7zAFwo0o9ocRblSypcVynJA"
                }
            ],
            "status": "OK"
        }
        */
    }

    private Object useGooglePlaceDetailsApi(String placeId, String... fields) {
        fields = fields.length == 0 ? new String[]{"name", "address_components", "formatted_address", "geometry"} : fields;
        String url = placesDetailsUrl + "?place_id=" + placeId +
                "&fields=" + String.join(",", fields) +
                "&key=" + env.getProperty("GOOGLE_API_KEY");

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

}
