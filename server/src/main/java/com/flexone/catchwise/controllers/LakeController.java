package com.flexone.catchwise.controllers;

import com.flexone.catchwise.domain.Lake;
import com.flexone.catchwise.dto.LakeResponse;
import com.flexone.catchwise.services.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lakes")
@RequiredArgsConstructor
public class LakeController {

    final LakeService lakeService;

    @GetMapping
    public List<Lake> getLakes() {
        return lakeService.getLakes();
    }

    @GetMapping("/{id}")
    public LakeResponse getLakeById(@PathVariable Long id) {
        return lakeService.getLakeById(id);
    }
}
