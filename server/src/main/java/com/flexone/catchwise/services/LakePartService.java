package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakePart;

import java.util.List;

public interface LakePartService {


    // region SINGLES
    LakePart findById(Long id);
    LakePart findByLocalId(String localId);
    Lake getParent(LakePart lakePart);
    // endregion


    // region LISTS



    // endregion


}
