package com.flexone.catchwise.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String family = "Unknown";

    @Column(unique = true, nullable = false)
    private String species;

    @Column(length = 2000)
    private String description = "";

    @Column(length = 2000)
    private String identification = "";

    @ElementCollection
    @CollectionTable(name = "common_fish_names", joinColumns = @JoinColumn(name = "fish_id"))
    private String[] commonNames = {};

//    @ManyToMany(mappedBy = "fish", cascade = CascadeType.MERGE)
//    @JsonIgnore
//    private LakeComponent[] lakeComponents = {};
//
//    @ManyToMany(mappedBy = "fish", cascade = CascadeType.MERGE)
//    @JsonIgnore
//    private Lake[] lakes = {};



}
