package com.flexone.catchwise.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/google")
public class GoogleApiController {


    @GetMapping("/place/details")
    public ResponseEntity<Object> getPlaceDetails() {
        return ResponseEntity.ok().build();
    }


}
