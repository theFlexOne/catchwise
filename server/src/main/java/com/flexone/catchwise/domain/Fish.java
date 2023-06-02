package com.flexone.catchwise.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String family = "Unknown";

    @Column(unique = true, nullable = false)
    private String species;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String identification;

    @ElementCollection
    @CollectionTable(name = "common_fish_names", joinColumns = @JoinColumn(name = "fish_id"))
    private String[] commonNames;

    @ManyToMany(mappedBy = "fishes", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Lake> lakes = new HashSet<>();


}
