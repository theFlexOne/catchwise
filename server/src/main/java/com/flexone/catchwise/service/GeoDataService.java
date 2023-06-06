package com.flexone.catchwise.service;

import com.flexone.catchwise.bootstrap.dto.GeoJsonFeatureCollection;
import com.flexone.catchwise.domain.GeoData;
import com.flexone.catchwise.repository.GeoDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class GeoDataService {

    final GeoDataRepository geoDataRepository;


    public void saveGeoJsonFeatures(GeoJsonFeatureCollection geoJsonFeatureCollection) {
        geoJsonFeatureCollection.getFeatures().forEach(geoJsonFeature -> {
            GeoData geoData = new GeoData()
                    .setProperties(geoJsonFeature.getProperties().toString())
                    .setGeometry(geoJsonFeature.getGeometry());
            geoDataRepository.save(geoData);
        });
    }


}
