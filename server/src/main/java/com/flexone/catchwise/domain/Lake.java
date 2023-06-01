package com.flexone.catchwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Accessors(chain = true)
@DiscriminatorValue("lake")
@RequiredArgsConstructor
@AllArgsConstructor
public class Lake extends LakeBase<Lake> {


    @OneToMany(mappedBy = "lake", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("lake")
    private Set<LakePart> lakeParts = new HashSet<>();


    public void setLakeParts(Set<LakePart> lakeParts) {
        this.lakeParts = lakeParts;
    }

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
