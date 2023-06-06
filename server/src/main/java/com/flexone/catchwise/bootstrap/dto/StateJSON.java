package com.flexone.catchwise.bootstrap.dto;

import com.flexone.catchwise.domain.CW_Geometry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
public class StateJSON {

    private String statefp;
    private String statens;
    private String affgeoid;
    private String geoid;
    private String stusps;
    private String name;
    private String lsad;
    private Long aland;
    private Long awater;
    private CW_Geometry CWGeometry;
    private CountyJSON[] counties;



}
