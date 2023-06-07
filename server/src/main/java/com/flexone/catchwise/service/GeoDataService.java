package com.flexone.catchwise.service;

import com.flexone.catchwise.bootstrap.dto.GeoJsonFeatureCollection;
import com.flexone.catchwise.domain.GeoData;
import com.flexone.catchwise.repository.GeoDataRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GeoDataService {

    final GeoDataRepository geoDataRepository;


//    public void saveGeoJsonFeatures(GeoJsonFeatureCollection geoJsonFeatureCollection) {
//        geoJsonFeatureCollection.getFeatures().forEach(geoJsonFeature -> {
//            GeometryFactory geometryFactory = new GeometryFactory();
//            GeometryCollection geometryCollection = new GeometryCollection(new Geometry[]{geoJsonFeature.getGeometry()}, geometryFactory);
//            GeoData geoData = GeoData.builder()
//                    .geometry(geometryCollection)
//                    .properties(geoJsonFeature.getProperties().toString())
//                    .build();
//
//            geoDataRepository.save(geoData);
//        });
    }


//}
