package com.gstech.AssistantAi.repositories;

import com.gstech.AssistantAi.model.BbqMenuDefinition;
import com.gstech.AssistantAi.model.enums.BBQ;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface BbqMenuRepository extends JpaRepository<BbqMenuDefinition, BBQ> {

    @EntityGraph(attributePaths = "items")
    Optional<BbqMenuDefinition> findByType(BBQ type);

    @Query("SELECT menu.basePrice FROM BbqMenuDefinition menu WHERE menu.type = :type")
    Optional<BigDecimal> findBasePriceByType(@Param("type") BBQ type);
}
