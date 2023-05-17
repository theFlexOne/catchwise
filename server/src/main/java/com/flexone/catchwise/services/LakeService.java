package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeResponse;

import java.util.List;

public interface LakeService {

    LakeResponse getLakeById(Long id);
    List<Lake> getLakes();
}
