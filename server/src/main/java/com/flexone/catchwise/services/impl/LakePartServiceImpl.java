package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.domain.LakePart;
import com.flexone.catchwise.repositories.LakeBaseRepository;
import com.flexone.catchwise.services.LakePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LakePartServiceImpl implements LakePartService {

    final LakeBaseRepository lakeBaseRepository;

    @Override
    public LakePart findById(Long id) {
        return null;
    }

    @Override
    public LakePart findByLocalId(String localId) {
        return null;
    }

    @Override
    public Lake getParent(LakePart lakePart) {
        return null;
    }
}
