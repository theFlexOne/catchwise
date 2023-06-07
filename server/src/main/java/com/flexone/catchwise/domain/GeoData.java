package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.locationtech.jts.geom.Geometry;

import java.util.Map;

@Entity
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "properties", length = 10000)
    private String properties;

    @Column(name = "geometry", columnDefinition = "GEOMETRY")
    private Geometry geometry;

    public GeoData(Geometry geometry, String properties) {
        this.geometry = geometry;
        this.properties = properties;
    }

}