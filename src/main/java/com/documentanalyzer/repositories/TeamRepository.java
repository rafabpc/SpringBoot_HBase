package com.documentanalyzer.repositories;

import com.documentanalyzer.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
