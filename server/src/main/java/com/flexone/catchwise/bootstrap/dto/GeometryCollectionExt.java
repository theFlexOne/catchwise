package com.flexone.catchwise.bootstrap.dto;

import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;

public class GeometryCollectionExt extends GeometryCollection {

  public GeometryCollectionExt() {
    super(null, new GeometryFactory());
  }
}
