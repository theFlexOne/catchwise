package com.flexone.catchwise.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@AllArgsConstructor
    public abstract class LakeBase<L extends LakeBase<L>> {

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
    @JoinTable(name = "lake_fish",
            joinColumns = @JoinColumn(name = "lake_id"),
            inverseJoinColumns = @JoinColumn(name = "fish_id"))
    private Set<Fish> fishes = new HashSet<>();

    public <T extends LakeBase<L>> LakeBase() {
    }

    public String buildLakeFishUrl() {
        return "/api/v1/lakes/" + this.id + "/fish";
    }

    public String buildLakePartsUrl() {
        return "/api/v1/lakes/" + this.id + "/lakeParts";
    }
}
