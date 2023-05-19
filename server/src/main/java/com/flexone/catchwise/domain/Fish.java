package com.flexone.catchwise.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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


}
