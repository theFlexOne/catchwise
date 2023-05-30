package com.flexone.catchwise.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flexone.catchwise.domain.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class LakeJSON {

    private Long id;
    private String name;
    private String localId;
    private Long countyId;
    private String nearestTown;
    private Coordinates coordinates;
    private FishSpecies[] fishSpecies;
    private String type;

    @Data
    public static class FishSpecies {
        String name;
        String species;
    }
}
