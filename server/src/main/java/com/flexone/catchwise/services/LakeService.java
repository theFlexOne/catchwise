package com.flexone.catchwise.services;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface LakeService {

    LakeResponse getLakeById(Long id);
    List<Lake> getLakes();

    Page<LakeResponse> findAll(int page, int size, Sort.Direction direction, String sortProperty);
}
