package com.flexone.catchwise.dto;

import com.flexone.catchwise.domain.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LakeJSON {

    private Long id;
    private String name;
    private String localId;
    private String state;
    private String county;
    private Integer countyId;
    private String nearestTown;
    private Coordinates coordinates;
    private FishSpecies[] fishSpecies;

    @Data
    public static class FishSpecies {
        String name;
        String species;
    }
}
