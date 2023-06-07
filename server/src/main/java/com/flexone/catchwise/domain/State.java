package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String abbr;

    private String fipsCode;

    private String nsCode;

    @OneToMany(
            mappedBy = "state",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<County> counties = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "state_geo_data",
            joinColumns = {@JoinColumn(name = "state_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "geo_data_id", referencedColumnName = "id")}
    )
    private GeoData geoData;


    public State addCounties(List<County> countyList) {
        this.counties.addAll(countyList);
        countyList.forEach(county -> county.setState(this));
        return this;
    }


}
