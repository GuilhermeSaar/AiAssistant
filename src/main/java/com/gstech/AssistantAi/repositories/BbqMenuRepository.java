package com.gstech.AssistantAi.repositories;

import com.gstech.AssistantAi.model.BbqMenuDefinition;
import com.gstech.AssistantAi.model.enums.BBQ;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BbqMenuRepository extends JpaRepository<BbqMenuDefinition, Long> {

    @EntityGraph(attributePaths = "items")
    Optional<BbqMenuDefinition> findByType(BBQ type);
}
