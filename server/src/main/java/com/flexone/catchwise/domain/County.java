package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class County {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "county_geo_data",
            joinColumns = @JoinColumn(name = "county_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "geo_data_id", referencedColumnName = "id")
    )
    private GeoData geoData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Lake> lakes;

}
