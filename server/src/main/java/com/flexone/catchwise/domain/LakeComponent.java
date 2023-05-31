package com.flexone.catchwise.domain;

import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
public class LakeComponent extends LakeBase {


    public Lake getParent() {
        String parentLocalId = this.getLocalId().substring(0, this.getLocalId().length() - 1) + "0";
        return lakeBaseService.findByLocalId(parentLocalId);
    }

}
