package com.flexone.catchwise.bootstrap.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flexone.catchwise.domain.Geometry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
    private Geometry geometry;
}
