package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakeComponent;
import com.flexone.catchwise.repositories.LakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LakePartService {

    final LakeRepository lakeRepository;

    public LakeComponent findById(Long id) {
        return null;
    }

    public LakeComponent findByLocalId(String localId) {
        return null;
    }

    public Lake getParent(LakeComponent lakeComponent) {
        return null;
    }
}
