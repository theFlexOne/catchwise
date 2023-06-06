package com.flexone.catchwise.bootstrap.dto;

import com.flexone.catchwise.service.GeoDataService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoJsonFeatureCollection {
    private String type;
    private String name;
    private GeoJsonFeature crs;
    private List<GeoJsonFeature> features;
}
