package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lake extends LakeBase {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "lake_id", referencedColumnName = "id")
    private Set<LakeComponent> components = new HashSet<>();

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
