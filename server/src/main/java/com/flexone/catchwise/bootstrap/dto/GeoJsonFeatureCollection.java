package com.flexone.catchwise.bootstrap.dto;

import com.flexone.catchwise.service.GeoDataService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
public class GeoJsonFeatureCollection {
    private String type;
    private String name;
    private GeoJsonFeature crs;
    private List<GeoJsonFeature> features;

    public GeoJsonFeatureCollection(String type, String name, GeoJsonFeature crs, List<GeoJsonFeature> features) {
        this.type = type;
        this.name = name;
        this.crs = crs;
        this.features = features;
    }


}
