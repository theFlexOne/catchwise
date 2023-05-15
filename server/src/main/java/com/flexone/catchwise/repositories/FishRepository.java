package com.flexone.catchwise.repositories;

import com.flexone.catchwise.domain.Fish;
import org.springframework.data.repository.CrudRepository;

public interface FishRepository extends CrudRepository<Fish, Long> {
}
