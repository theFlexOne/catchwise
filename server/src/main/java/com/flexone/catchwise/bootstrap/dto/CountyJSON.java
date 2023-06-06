package com.flexone.catchwise.bootstrap.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.geolatte.geom.FeatureCollection;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class CountyJSON {

    private String countyfp;
    private String countyns;
    private String affgeoid;
    private String geoid;
    private String name;
    private String lsad;
    private Long aland;
    private Long awater;
}
