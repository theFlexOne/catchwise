package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String localId;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "county_id", referencedColumnName = "id")
    private County county;

    private String nearestTown;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "lake_fish", joinColumns = @JoinColumn(name = "lake_id"), inverseJoinColumns = @JoinColumn(name = "fish_id"))
    private Set<Fish> fish = new HashSet<>();

    public String buildLakeFishUrl() {
        return "/api/v1/lakes/" + this.id + "/fish";
    }

    public Double getLat() {
        return this.coordinates.getLatitude();
    }
    public Double getLng() {
        return this.coordinates.getLongitude();
    }
    public State getState() {
        return this.county.getState();
    }
}
