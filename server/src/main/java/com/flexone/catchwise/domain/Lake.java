package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Lake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String localId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "county_id", referencedColumnName = "id")
    private County county;

    private String nearestTown;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "lake_fish",
            joinColumns = @JoinColumn(name = "lake_id"),
            inverseJoinColumns = @JoinColumn(name = "fish_id"))
    private Set<Fish> fishes;

    public String buildLakeFishUrl() {
        return "/api/v1/lakes/" + this.id + "/fish";
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<LakeComponent> components;

    public Double getLat() {
        return this.getCoordinates().getLatitude();
    }
    public Double getLng() {
        return this.getCoordinates().getLongitude();
    }
    public State getState() {
        return this.getCounty().getState();
    }
}
