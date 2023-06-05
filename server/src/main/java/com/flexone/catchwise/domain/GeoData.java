package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GeoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String fipsId;
    private String gnisId;

    @Column(unique = true)
    private String geoId;

    private String americanFactFinderId;

    private String lsad;

    private Long landArea;
    private Long waterArea;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geometry_id")
    private Geometry geometry;

    @OneToOne(mappedBy = "geoData")
    private State state;

    @OneToOne(mappedBy = "geoData")
    private County county;


}
