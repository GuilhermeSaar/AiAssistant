package com.gstech.AssistantAi.repositories;

import com.gstech.AssistantAi.model.Meat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeatRepository extends JpaRepository<Meat, Long> {
}
