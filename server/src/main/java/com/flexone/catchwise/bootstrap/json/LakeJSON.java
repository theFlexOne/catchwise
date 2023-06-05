package com.flexone.catchwise.bootstrap.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flexone.catchwise.domain.Coordinates;
import lombok.*;

import java.util.HashSet;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class LakeJSON {

    private Long id;
    private String name;
    private String localId;

    private String countyFips;

    private Long countyId;
    private String nearestTown;
    private Coordinates coordinates;

    @JsonProperty("fish")
    private HashSet<FishSpecies> fishes;

    private List<LakeJSON> components;

    @Data
    public static class FishSpecies {
        String name;
        String species;
    }
}
