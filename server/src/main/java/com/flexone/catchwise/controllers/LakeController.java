package com.flexone.catchwise.controllers;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeResponse;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lakes")
@RequiredArgsConstructor
public class LakeController {

    final LakeService lakeService;

    @GetMapping
    public Page<LakeResponse> getAllLakes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String sortProperty) {
        return lakeService.findAll(page, size, direction, sortProperty);
    }


    @GetMapping("/{id}")
    public LakeResponse getLakeById(@PathVariable Long id) {
        return lakeService.getLakeById(id);
    }
}
