package com.gstech.AssistantAi.dto;

import com.gstech.AssistantAi.model.enums.NameDrink;

import java.math.BigDecimal;
import java.util.Map;

public record DrinkOption(

        String title,
        Map<NameDrink, BigDecimal> drink
) {
}
