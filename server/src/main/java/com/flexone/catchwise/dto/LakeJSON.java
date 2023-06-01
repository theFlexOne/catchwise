package com.flexone.catchwise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flexone.catchwise.domain.Coordinates;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
public class LakeJSON {

    private String name;
    private String localId;
    private Long countyId;
    private String nearestTown;
    private Coordinates coordinates;
    private FishSpecies[] fish = {};
    private Component[] components;


    @Data
    public static class Component {
        String localId;
        String name;
        Coordinates coordinates;
        FishSpecies[] fish;
    }
    @Data
    public static class FishSpecies {
        String name;
        String species;
    }
}
