package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.LakeComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;

public interface LakeComponentRepository extends JpaRepository<LakeComponent, Long> {

}
