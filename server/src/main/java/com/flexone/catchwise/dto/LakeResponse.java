package com.flexone.catchwise.dto;

import com.flexone.catchwise.domain.Coordinates;
import com.flexone.catchwise.domain.County;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class LakeResponse {

    private Long id;

    private String name;
    private String localId;
    private String county;
    private String state;
    private String nearestTown;
    private Coordinates coordinates;

    private String fishesUrl;
    private String lakePartsUrl;


}
