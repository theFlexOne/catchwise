package com.flexone.catchwise.bootstrap.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.*;

import java.util.Map;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class GeoJsonFeature {
  private final GeometryFactory geometryFactory = new GeometryFactory();

  final String type = "Feature";
  private MultiPolygon geometry;
  private Map<String, Object> properties;

  public GeoJsonFeature(Geometry geometry, Map<String, Object> properties) {
    this.geometry = geometryFactory.createMultiPolygon(new Polygon[] {(Polygon) geometry});
    this.properties = properties;
  }
}
