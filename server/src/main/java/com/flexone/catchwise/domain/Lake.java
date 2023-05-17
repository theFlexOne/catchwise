package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flexone.catchwise.dto.LakeFishResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String localId;
    private String state;
    private String county;
    private Integer countyId;
    private String nearestTown;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "lake_fish", joinColumns = @JoinColumn(name = "lake_id"), inverseJoinColumns = @JoinColumn(name = "fish_id"))
    private Set<Fish> fish = new HashSet<>();

    public List<LakeFishResponse> getLakeFishResponses() {
        return fish.stream()
                .map(f -> new LakeFishResponse(f.getId(), "/fish/" + f.getId()))
                .collect(Collectors.toList());
    }
}
