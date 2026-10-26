package com.gstech.AssistantAi.repositories;

import com.gstech.AssistantAi.model.Drink;
import com.gstech.AssistantAi.model.enums.NameDrink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

    List<Drink> findByCategory_CategoryNameIgnoreCase(String categoryName);

    @Query("SELECT d.price FROM Drink d WHERE d.nameDrink = :nameDrink")
    BigDecimal findPriceByNameDrink(@Param("nameDrink") NameDrink nameDrink);

}
