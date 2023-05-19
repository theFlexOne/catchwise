package com.flexone.catchwise.services.impl;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeResponse;
import com.flexone.catchwise.mapper.FishToLakeFishResponseMapper;
import com.flexone.catchwise.repositories.LakeRepository;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LakeServiceImpl  implements LakeService {

    final LakeRepository lakeRepository;
    final FishToLakeFishResponseMapper fishMapper;

    @Override
    public Lake findById(Long id) {
        return lakeRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Lake> findAll(int page, int size, Sort.Direction direction, String sortProperty) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortProperty));
        return lakeRepository.findAll(pageable);
    }
}
