package com.flexone.catchwise.bootstrap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoJsonFeature {
    private String type;
    private Geometry geometry;
    private Map<String, Object> properties;
}
