package com.flexone.catchwise.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("lakePart")
@Data
@Entity
@Accessors(chain = true)
@RequiredArgsConstructor
public class LakePart extends LakeBase {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lake_id", referencedColumnName = "id")
    private Lake lake;


}
