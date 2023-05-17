package com.flexone.catchwise.dto;

import com.flexone.catchwise.domain.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LakeResponse {

    private Long id;

    private String name;
    private String localId;
    private String state;
    private String county;
    private Integer countyId;
    private String nearestTown;
    private Coordinates coordinates;

    private List<LakeFishResponse> fish;


}
