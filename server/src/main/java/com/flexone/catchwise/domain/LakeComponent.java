package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor(force = true)
@Accessors(chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class LakeComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String localId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "lake_component_fish",
            joinColumns = @JoinColumn(name = "lake_id"),
            inverseJoinColumns = @JoinColumn(name = "fish_id"))
    private Set<Fish> fishes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "lake_id")
    @JsonIgnore
    private Lake lake;

    public String getComponentsUrl() {
        return "/api/v1/lakes/" + this.id + "/lakeComponents";
    }

}
