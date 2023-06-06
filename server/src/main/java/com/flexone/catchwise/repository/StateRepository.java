package com.flexone.catchwise.repository;

import com.flexone.catchwise.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {

    State findByName(String state);

    State findByAbbr(String state);

}
