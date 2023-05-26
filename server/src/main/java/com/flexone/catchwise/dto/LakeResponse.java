package com.flexone.catchwise.dto;

import com.flexone.catchwise.domain.Coordinates;
import com.flexone.catchwise.domain.County;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LakeResponse {

    private Long id;

    private String name;
    private String localId;
    private String county;
    private String state;
    private String nearestTown;
    private Coordinates coordinates;

    private String fishUrl;


}
