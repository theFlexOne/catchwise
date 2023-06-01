package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lake {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name = "unnamed";
    private String localId;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "county_id", referencedColumnName = "id")
    private County county;

    private String nearestTown;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "lake_fish",
            joinColumns = @JoinColumn(name = "lake_id"),
            inverseJoinColumns = @JoinColumn(name = "fish_id"))
    private Set<Fish> fish = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lake")
    private Set<LakeComponent> lakeComponents = new HashSet<>();

    public Double getLat() {
        return this.getCoordinates().getLatitude();
    }
    public Double getLng() {
        return this.getCoordinates().getLongitude();
    }
    public State getState() {
        return this.getCounty().getState();
    }

    public String buildLakeFishUrl() {
        return "/api/v1/lakes/" + this.getId() + "/fish";
    }


}
